import axios from "axios";

const instance = axios.create({
  baseURL: "http://localhost:8080",
  headers: { "Content-Type": "application/json" },
});

// Utiliza Redux para almacenar el token
export const setAuthHeader = (token) => {
  if (token) {
    instance.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  } else {
    delete instance.defaults.headers.common["Authorization"];
  }
};

export const request = (method, url, data) => {
  return instance({ method, url, data });
};
