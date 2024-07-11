package com.literalura.service;

import java.util.List;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
    <T> List<T> obtenerListaDatos(String json, Class<T> clase);

}
