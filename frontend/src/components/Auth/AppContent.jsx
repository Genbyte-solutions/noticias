/* eslint-disable react/prop-types */
import React from "react";
import { connect } from "react-redux";
import { logout } from "../../reducer/authActions.js";
import LoginForm from "../../pages/login/Login"; // Asume que has creado este componente

class AppContent extends React.Component {
  render() {
    return (
      <div>
        {this.props.isAuthenticated ? (
          // Contenido protegido
          <div>
            <h1>Contenido Protegido</h1>
            <p>Bienvenido a la sección protegida de la aplicación.</p>
            <button onClick={this.props.logout}>Cerrar sesión</button>
          </div>
        ) : (
          // Formulario de inicio de sesión
          <LoginForm onLogin={this.onLogin} />
        )}
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  isAuthenticated: state.auth.isAuthenticated,
});

const mapDispatchToProps = {
  logout,
};

export default connect(mapStateToProps, mapDispatchToProps)(AppContent);
