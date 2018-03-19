/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactos.controlador;

import contactos.modelo.Persona;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class BD {

    private final String URL = "jdbc:derby://localhost:1527/contactos";
    private final String USUARIO = "administrador";
    private final String CLAVE = "admin";

    private Connection conexion;
    private PreparedStatement seleccionarPersonas;
    private PreparedStatement seleccionarPersonasPorApellido;
    private PreparedStatement insertarNuevaPersona;

    public BD() {
        try {
            conexion = DriverManager.getConnection(URL, "administrador", "admin");
        } catch (Exception e) {
        }
    }

    public List<Persona> getPersonas() {
        List<Persona> lista = new ArrayList<>();
        try {
            seleccionarPersonas = conexion.prepareStatement("select * from PERSONA ");
            ResultSet resultado = seleccionarPersonas.executeQuery();
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nombres = resultado.getString("nombres");
                String apellidos = resultado.getString("apellidos");
                String email = resultado.getString("email");
                String telefono = resultado.getString("telefono");
                lista.add(new Persona(id, nombres, apellidos, email, telefono));
            }
            resultado.close();
            conexion.close();
        } catch (SQLException ex) {
            System.out.println("Error al enviar consulta " + ex);
        }
        return lista;
    }

    public List<Persona> getPersonaPorApellido(String apellido) {
        List<Persona> lista = new ArrayList<>();
        try {
            seleccionarPersonasPorApellido = conexion.prepareStatement("select * from persona where apellidos" + apellido);
            ResultSet resultado = seleccionarPersonasPorApellido.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nombres = resultado.getString("nombres");
                String apellidos = resultado.getString("apellidos");
                String email = resultado.getString("email");
                String telefono = resultado.getString("telefono");
                lista.add(new Persona(id, nombres, apellidos, email, telefono));
            }
            resultado.close();
            conexion.close();
        } catch (SQLException ex) {
            System.out.println("Error al enviar consulta " + ex);
        }
        return lista;
    }

    public int agregarPersona(String nombre, String apellido, String email, String telefono) {
        Persona pers = new Persona(this.getPersonas().size() + 1, nombre, apellido, email, telefono);
        try {
            insertarNuevaPersona = conexion.prepareStatement("insert into persona values(" + (this.getPersonas().size() + 1) + ", '" + nombre + "', '" + apellido + "', '" + email + "', '" + telefono + "')");
            return insertarNuevaPersona.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;

    }
}
