import React, { useState } from "react";
import axios from "axios";
import "./App.css"; // Подключаем стили

const Register = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [showSuccess, setShowSuccess] = useState(false); // Для показа анимации

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post("http://localhost:8080/auth/register", {
        email,
        password,
      });
      setMessage("Registration successful!");
      setShowSuccess(true);

      // Скрываем анимацию через 2 секунды
      setTimeout(() => {
        setShowSuccess(false);
      }, 2000);
    } catch (error) {
      setMessage(
        "Error: " +
          (error.response
            ? `${error.response.status} - ${error.response.data}`
            : "Network error")
      );
    }
  };

  return (
    <div className="container">
      <h2>Register</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="email"
          placeholder="Enter your email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Enter your password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit">Register</button>
      </form>
      {message && <p>{message}</p>}

      {/* Всплывающее сообщение с анимацией */}
      {showSuccess && (
        <div className="success-message">
          Успех
        </div>
      )}
    </div>
  );
};

export default Register;
