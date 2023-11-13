/* eslint-disable no-unused-vars */
import React, { useEffect, useRef, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useParams } from "react-router-dom";
import Notification from '../../../components/notificacion/Notificacion';
import { imagenPorIdUsuario } from '../../../service/imagen/Imagen.js';
import { usuarioPorId } from '../../../service/usuario/Listar.js';

function EditarUsuario() {
    const { id } = useParams();
    const [usuario, setUsuario] = useState({});
    const [imagen, setImagen] = useState();
    const { register, handleSubmit, setValue, formState: { errors } } = useForm();
    const [imagenName, setImagenName] = useState('Subir imagen');
    const dataRef = useRef(null);
    const [showNotification, setShowNotification] = useState(false);
    const [notificationMessage, setNotificationMessage] = useState('');
    
    useEffect(() => {
        imagenPorIdUsuario(id)
            .then(data => {
                setImagen(data);
            })
            .catch(error => console.error('Error al obtener la imagen de usuario: ', error));
    }, [id]);

    useEffect(() => {
        usuarioPorId(id)
            .then(data => {
                setUsuario(data);
                // Al recibir los datos del autor, actualiza los campos de entrada
                setValue('nombre', data.nombre);
                setValue('apellido', data.apellido);
                setValue('nombreUsuario', data.nombreUsuario);
                setValue('email', data.email);
                setValue('telefono', data.telefono);
                setValue('password', data.password);
                setValue('password2', data.password);
            })
            .catch(error => console.error('Error al obtener el usuario: ', error));
    }, [id, setValue, imagen]);

    const handleNotificationClose = () => {
        setShowNotification(false);
        setNotificationMessage('');
    };

    useEffect(() => {
        if (errors.imagen) {
            setImagenName(errors.imagen.message);
        } else {
            const imagen = dataRef.current ? dataRef.current.imagen : null;
            setImagenName(imagen ? imagen[0].name : 'Subir imagen');
        }
    }, [errors.imagen, dataRef]);

    const onSubmit = async (data) => {
        dataRef.current = { ...data };
        const formData = new FormData();
        formData.append('nombre', data.nombre);
        formData.append('apellido', data.apellido);
        formData.append('nombreUsuario', data.nombreUsuario);
        formData.append('imagen', data.imagen[0]);
        formData.append('email', data.email);
        formData.append('telefono', data.telefono);
        formData.append('password', data.password);
        formData.append('password2', data.password2);

        try {
            const response = await fetch(`http://localhost:8080/api/usuario/editar/${id}`, {
                method: 'POST',
                body: formData,
            });

            if (response.ok) {
                const responseData = await response.text();
                setNotificationMessage(responseData);
                setShowNotification(true);
            } else {
                setNotificationMessage('Error al enviar el formulario. Response not ok: ' + response.statusText);
                setShowNotification(true);
            }
        } catch (error) {
            setNotificationMessage('Error al enviar el formulario: ' + error.message);
            setShowNotification(true);
        }
    };

    return (
        <section className='flexCT'>
            <div className='form-container'>
                <form onSubmit={handleSubmit(onSubmit)} className='form'>
                    <span className="titulo">Editar Usuario</span>
                    {imagen && <img src={imagen} alt="imagen" />}
                    <div>
                        <label htmlFor="nombre">Nombre</label>
                        <input
                            type="text"
                            id="nombre"
                            {...register('nombre', { required: 'Por favor, completa este campo.' })}
                            className='input'
                            defaultValue={_ => usuario.nombre}
                        />
                        {errors.nombre && <span className="error-msg">{errors.nombre.message}</span>}
                    </div>
                    <div>
                        <label htmlFor="apellido">Apellido</label>
                        <input
                            type="text"
                            id="apellido"
                            {...register('apellido', { required: 'Por favor, completa este campo.' })}
                            className='input'
                            defaultValue={_ => usuario.apellido}
                        />
                        {errors.apellido && <span className="error-msg">{errors.apellido.message}</span>}
                    </div>
                    <div>
                        <label htmlFor="nombreUsuario">Nombre de usuario</label>
                        <input
                            type="text"
                            id="nombreUsuario"
                            {...register('nombreUsuario', { required: 'Por favor, completa este campo.' })}
                            className='input'
                            defaultValue={_ => usuario.nombreUsuario}
                        />
                        {errors.nombreUsuario && <span className="error-msg">{errors.nombreUsuario.message}</span>}
                    </div>
                    <div>
                        <label className="cargar-archivo input" htmlFor="imagen">
                            <div className="icono">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="" viewBox="0 0 24 24">
                                    <g strokeWidth="0" id="SVGRepo_bgCarrier"></g>
                                    <g strokeLinejoin="round" strokeLinecap="round" id="SVGRepo_tracerCarrier"></g>
                                    <g id="SVGRepo_iconCarrier">
                                        <path fill="" d="M10 1C9.73478 1 9.48043 1.10536 9.29289 1.29289L3.29289 7.29289C3.10536 7.48043 3 7.73478 3 8V20C3 21.6569 4.34315 23 6 23H7C7.55228 23 8 22.5523 8 22C8 21.4477 7.55228 21 7 21H6C5.44772 21 5 20.5523 5 20V9H10C10.5523 9 11 8.55228 11 8V3H18C18.5523 3 19 3.44772 19 4V9C19 9.55228 19.4477 10 20 10C20.5523 10 21 9.55228 21 9V4C21 2.34315 19.6569 1 18 1H10ZM9 7H6.41421L9 4.41421V7ZM14 15.5C14 14.1193 15.1193 13 16.5 13C17.8807 13 19 14.1193 19 15.5V16V17H20C21.1046 17 22 17.8954 22 19C22 20.1046 21.1046 21 20 21H13C11.8954 21 11 20.1046 11 19C11 17.8954 11.8954 17 13 17H14V16V15.5ZM16.5 11C14.142 11 12.2076 12.8136 12.0156 15.122C10.2825 15.5606 9 17.1305 9 19C9 21.2091 10.7909 23 13 23H20C22.2091 23 24 21.2091 24 19C24 17.1305 22.7175 15.5606 20.9844 15.122C20.7924 12.8136 18.858 11 16.5 11Z" clipRule="evenodd" fillRule="evenodd"></path>
                                    </g>
                                </svg>
                            </div>
                            <input
                                type='file'
                                id='imagen'
                                {...register('imagen', { required: 'Por favor, sube una imagen.' })}
                            />
                            <div className='texto'>
                                <span className={errors.imagen ? "error-msg" : ""}>
                                    {errors.imagen ? errors.imagen.message : imagenName}
                                </span>
                            </div>
                        </label>
                    </div>
                    <div>
                        <label htmlFor="email">Email</label>
                        <input
                            type="email"
                            id="email"
                            {...register('email', { required: 'Por favor, completa este campo.' })}
                            className='input'
                            defaultValue={_ => usuario.email}
                        />
                        {errors.email && <span className="error-msg">{errors.email.message}</span>}
                    </div>
                    <div>
                        <label htmlFor="telefono">Telefono</label>
                        <input
                            type="tel"
                            id="telefono"
                            {...register('telefono', { required: 'Por favor, completa este campo.' })}
                            className='input'
                            defaultValue={_ => usuario.telefono}
                        />
                        {errors.telefono && <span className="error-msg">{errors.telefono.message}</span>}
                    </div>
                    <div>
                        <label htmlFor="password">Contrasena</label>
                        <input
                            type="password"
                            id="password"
                            {...register('password', { required: 'Por favor, completa este campo.' })}
                            className='input'
                            defaultValue={_ => usuario.password}
                        />
                        {errors.password && <span className="error-msg">{errors.password.message}</span>}
                    </div>
                    <div>
                        <label htmlFor="password2">Repita contrasena</label>
                        <input
                            type="password"
                            id="password2"
                            {...register('password2', { required: 'Por favor, completa este campo.' })}
                            className='input'
                            defaultValue={_ => usuario.password}
                        />
                        {errors.password2 && <span className="error-msg">{errors.password2.message}</span>}
                    </div>
                    <div className="button-container">
                        <button type="submit" className="send-button">
                            Editar Usuario
                        </button>
                        <div className="reset-button-container">
                            <button id="reset-btn" className="reset-button" type='reset'>
                                Resetear
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            {showNotification && <Notification message={notificationMessage} onClose={handleNotificationClose} />}
        </section>
    );
}

export default EditarUsuario;