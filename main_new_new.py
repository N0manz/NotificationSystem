import logging
from flask import Flask, request, jsonify
import json
import time
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager

# Настройка логирования
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

app = Flask(__name__)

def get_password(site, username):
    logger.warning(f"{username} {site}")
    if site == "https://icispp.ru/" and username == "serg":
        return "Pafuda33"
    elif username == "mda045":
        return "XC8XG65XRZa"
    elif site == "https://auth.mephi.ru" and username == "bsa023":
        return "Pafuda33"
    return None

def parse_notifications(username, password, service_link):
    options = webdriver.ChromeOptions()
    options.add_argument("--disable-gpu")
    options.add_argument("--no-sandbox")
    options.add_argument("--headless")

    driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)

    try:
        # Авторизация
        logger.info(f"Парсинг уведомлений для пользователя {username} на {service_link}")
        driver.get(service_link + "/login")  # Используем ссылку на сервис
        time.sleep(2)

        username_input = driver.find_element(By.NAME, "username")
        password_input = driver.find_element(By.NAME, "password")
        username_input.send_keys(username)
        password_input.send_keys(password)
        password_input.send_keys(Keys.RETURN)
        time.sleep(3)

        # Переход на страницу уведомлений
        driver.get("https://home.mephi.ru/notifications")  # Переход на страницу уведомлений
        time.sleep(3)

        # Ждём уведомления
        WebDriverWait(driver, 10).until(
            EC.presence_of_all_elements_located((By.CLASS_NAME, "notification"))
        )
        notifications = driver.find_elements(By.CLASS_NAME, "notification")

        notifications_data = []
        for notification in notifications[:3]:
            title = notification.find_element(By.CLASS_NAME, "card-title").text
            link = notification.find_element(By.TAG_NAME, "a").get_attribute("href")
            notifications_data.append({
                "title": title,
                "link": link
            })

        return notifications_data

    except Exception as e:
        logger.error(f"Ошибка при парсинге для {username}: {e}")
        return {"error": str(e)}

    finally:
        driver.quit()

def parse_icispp(username, password, service_link):
    options = webdriver.ChromeOptions()
    options.add_argument("--disable-gpu")
    options.add_argument("--no-sandbox")
    options.add_argument("--headless")

    driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)

    try:
        # Авторизация
        logger.info(f"Парсинг данных для пользователя {username} на {service_link}")
        driver.get(service_link)
        time.sleep(3)

        username_input = driver.find_element(By.NAME, "login")
        password_input = driver.find_element(By.NAME, "password")
        username_input.send_keys(username)
        password_input.send_keys(password)
        driver.find_element(By.CLASS_NAME, "_button_t7l1o_1").click()
        time.sleep(3)

        # Ждём появления элемента с оценкой
        WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.CLASS_NAME, "_grades__controls_cmrlt_33"))
        )
        
        # Получаем итоговую оценку
        final_grade_element = driver.find_element(By.CLASS_NAME, "_grades__controls_cmrlt_33")
        final_grade = final_grade_element.find_element(By.TAG_NAME, "div").text

        # Формируем результат в формате уведомлений
        notifications_data = [{
                "title": final_grade,
                "link": service_link
            }]

        return notifications_data

    except Exception as e:
        logger.error(f"Ошибка при парсинге для {username} на {service_link}: {e}")
        return {"error": str(e)}

    finally:
        driver.quit()


@app.route('/parse-notifications', methods=['POST'])
def handle_notifications():
    logger.info("Получен POST-запрос на /parse-notifications")
    try:
        data = request.json
        logger.info(f"Данные запроса: {json.dumps(data, ensure_ascii=False, indent=4)}")
        results = []

        for service in data:
            username = service.get("username")
            service_link = service.get("link")  # Получаем ссылку на сервис

            if not username or not service_link:
                error_message = "Missing username or link"
                logger.warning(f"Ошибка в запросе: {error_message}")
                results.append({"error": error_message})
                continue

            password = get_password(service_link, username)

            if not password:
                error_message = f"Password not found for {username} on {service_link}"
                logger.warning(f"Ошибка в запросе: {error_message}")
                results.append({"error": error_message})
                continue

            if service_link == "https://icispp.ru/":
                parsed_data = parse_icispp(username, password, service_link)
                results.append({
                    "username": username,
                    "notifications": parsed_data
                })
            else:
                notifications = parse_notifications(username, password, service_link)
                results.append({
                    "username": username,
                    "notifications": notifications
                })

        return jsonify(results)

    except Exception as e:
        logger.error(f"Ошибка обработки запроса: {e}")
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)

