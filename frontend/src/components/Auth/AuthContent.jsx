import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { request } from "../../helpers/axios_helper.jsx";
import { setAuthHeader } from "../../reducer/authActions.js"; // Asegúrate de importar las acciones correctas

const AuthContent = () => {
  const dispatch = useDispatch();
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const data = useSelector((state) => state.auth.data);

  useEffect(() => {
    if (isAuthenticated) {
      request("GET", "/messages", {})
        .then((response) => {
          dispatch({ type: "SET_DATA", payload: response.data });
        })
        .catch((error) => {
          if (error.response.status === 401) {
            setAuthHeader(null);
            dispatch({ type: "LOGOUT" });
          } else {
            // Manejar el error de otra manera si es necesario
          }
        });
    }
  }, [isAuthenticated, dispatch]);

  return (
    <div className="row justify-content-md-center">
      <div className="col-4">
        <div className="card" style={{ width: "18rem" }}>
          <div className="card-body">
            <h5 className="card-title">Respuesta del servidor</h5>
            <p className="card-text">Contenido:</p>
            <ul>
              {data.map((line) => (
                <li key={line}>{line}</li>
              ))}
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AuthContent;
