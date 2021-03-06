package ejercicio.dao;

import ejercicio.Conexion;
import ejercicio.model.Direccion;
import ejercicio.model.Equipo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EquipoDaoImpl implements EquipoDao{
    private Conexion conection = new Conexion();

    @Override
    public void insert(Equipo equipo) {
        Connection conneccion = null;
        try{

            conneccion = conection.conection();

            try (Statement instruccion = conneccion.createStatement()){
                Integer cuitEquipo = equipo.getCuit();
                String nombreEquipo = equipo.getNombre();
                String emailEquipo = equipo.getEmail();
                Integer telefonoEquipo = equipo.getTelefono();
                LocalDate fechaFundacion = equipo.getFechaFundacion();
                LocalDate fechaPrimeraDivision = equipo.getFechaPrimeraDivision();
                String presidenteANEquipo = equipo.getPresidenteNombreApellido();
                Character categoriaEquipo = equipo.getCategoria();

                String sql = "INSERT INTO equipo(cuit_equipo, nombre, email, telefono, fecha_fundacion, fecha_primera_division, " +
                        "presidente_apellido_nombre, categoria)" +
                        "VALUES("+cuitEquipo+",'"+nombreEquipo+"','"+emailEquipo+"',"+telefonoEquipo+",'"+fechaFundacion+"','"+fechaPrimeraDivision+"','" +
                        presidenteANEquipo+"','"+categoriaEquipo+"');";
                instruccion.execute(sql);
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if(conneccion != null){
            try{
                conneccion.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insertEquipoConDireccion(Equipo equipo, Direccion direccion) {
        Connection conneccion = null;
        try{

            conneccion = conection.conection();

            try (Statement instruccion = conneccion.createStatement()){

                DireccionDao direccionDao = new DireccionDaoImpl();

                Integer cuitEquipo = equipo.getCuit();
                String nombreEquipo = equipo.getNombre();
                String emailEquipo = equipo.getEmail();
                Integer telefonoEquipo = equipo.getTelefono();
                LocalDate fechaFundacion = equipo.getFechaFundacion();
                LocalDate fechaPrimeraDivision = equipo.getFechaPrimeraDivision();
                String presidenteANEquipo = equipo.getPresidenteNombreApellido();
                Character categoriaEquipo = equipo.getCategoria();
                Integer idDireccion = direccionDao.obtenerIdDireccion(direccion);

                String sql = "INSERT INTO equipo(cuit_equipo, nombre, email, telefono, fecha_fundacion, fecha_primera_division, " +
                        "presidente_apellido_nombre, categoria, id_direccion)" +
                        "VALUES("+cuitEquipo+",'"+nombreEquipo+"','"+emailEquipo+"',"+telefonoEquipo+",'"+fechaFundacion+"','"+fechaPrimeraDivision+"','" +
                        presidenteANEquipo+"','"+categoriaEquipo+"',"+idDireccion+")";
                instruccion.execute(sql);
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if(conneccion != null){
            try{
                conneccion.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Equipo equipo, Integer cuit) {
        Connection conneccion = null;
        try{
            conneccion = conection.conection();

            try (Statement instruccion = conneccion.createStatement()){
                Integer cuitEquipo = equipo.getCuit();
                String nombreEquipo = equipo.getNombre();
                String emailEquipo = equipo.getEmail();
                Integer telefonoEquipo = equipo.getTelefono();
                LocalDate fechaFundacion = equipo.getFechaFundacion();
                LocalDate fechaPrimeraDivision = equipo.getFechaPrimeraDivision();
                String presidenteANEquipo = equipo.getPresidenteNombreApellido();
                Character categoriaEquipo = equipo.getCategoria();

                String sql = "UPDATE equipo SET cuit_equipo = " + cuitEquipo +
                        " , nombre = '" + nombreEquipo +
                        "' , email = '" + emailEquipo +
                        "' , telefono = " + telefonoEquipo +
                        " , fecha_fundacion = " + fechaFundacion +
                        " , fecha_primera_division = " + fechaPrimeraDivision +
                        " , presidente_apellido_nombre = '" + presidenteANEquipo +
                        "' , categoria = '" + categoriaEquipo +
                        "' WHERE cuit_equipo = " + cuit;
                instruccion.execute(sql);
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if(conneccion != null){
            try{
                conneccion.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void read() {
        Connection conneccion = null;

        try{
            conneccion = conection.conection();

            try (Statement instruccion = conneccion.createStatement()){
                ResultSet resultado = instruccion.executeQuery("SELECT * FROM equipo;");
                List<Equipo> listaEquipos = new ArrayList<>();
                while (resultado.next()){
                    Integer cuitEq = resultado.getInt("cuit_equipo");
                    String nombreEq = resultado.getString("nombre");
                    String emailEq = resultado.getString("email");
                    Integer telefonoEq = resultado.getInt("telefono");
                    LocalDate fechaFundacionEq = resultado.getDate("fecha_fundacion").toLocalDate();
                    LocalDate fechaPrimeraDivEq = resultado.getDate("fecha_primera_division").toLocalDate();
                    String presidenteEq = resultado.getString("presidente_apellido_nombre");
                    Character categoriaEq = resultado.getString("categoria").charAt(0);



                    Equipo equipo = new Equipo(cuitEq,nombreEq, fechaFundacionEq, presidenteEq, telefonoEq,
                            emailEq,fechaPrimeraDivEq,categoriaEq);

                    listaEquipos.add(equipo);
                }

                for (Equipo equipo: listaEquipos) {
                    System.out.println(equipo.toString());
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if(conneccion != null){
            try{
                conneccion.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Integer cuit) {
        Connection conneccion = null;
        try{
            conneccion = conection.conection();

            try (Statement instruccion = conneccion.createStatement()){

                String sql =  "DELETE FROM equipo WHERE equipo.cuit_equipo = "+ cuit +";";
                instruccion.execute(sql);

            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if(conneccion != null){
            try{
                conneccion.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void mostrarCantidadDeJugadoresActuales() {
        Connection conneccion = null;
        try{
            conneccion = conection.conection();

            try (Statement instruccion = conneccion.createStatement()){
                ResultSet resultado = instruccion.executeQuery("SELECT e.nombre as equipo_nombre, COUNT(h.jugador_dni) as cantidad_jugadores " +
                        "FROM equipo e JOIN historial h " +
                        "ON h.cuit_equipo = e.cuit_equipo " +
                        "WHERE h.fecha_fin is null " +
                        "GROUP BY e.nombre " +
                        "ORDER BY e.nombre ASC;");

                while (resultado.next()){
                    String nombreEq = resultado.getString("equipo_nombre");
                    Integer cantidadJugadoresEq = resultado.getInt("cantidad_jugadores");

                    System.out.println("El equipo " + nombreEq + " tiene "+ cantidadJugadoresEq + " jugadores actualmente.");
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if(conneccion != null){
            try{
                conneccion.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void mostrarCantidadDeDefensoresActuales() {
        Connection conneccion = null;
        try{
            conneccion = conection.conection();

            try (Statement instruccion = conneccion.createStatement()){
                ResultSet resultado = instruccion.executeQuery("SELECT e.nombre as equipo_nombre, COUNT(h.jugador_dni) as cantidad_jugadores " +
                        "FROM equipo e JOIN historial h " +
                        "ON h.cuit_equipo = e.cuit_equipo " +
                        "WHERE h.fecha_fin is null and h.posicion like 'defensor' " +
                        "GROUP BY e.nombre " +
                        "ORDER BY e.nombre ASC;");

                while (resultado.next()){
                    String nombreEq = resultado.getString("equipo_nombre");
                    Integer cantidadJugadoresEq = resultado.getInt("cantidad_jugadores");

                    System.out.println("El equipo " + nombreEq + " tiene "+ cantidadJugadoresEq + " defensores actualmente.");
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if(conneccion != null){
            try{
                conneccion.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void mostrarCantidadDeJugadoresEnUnaFecha(LocalDate fecha) {
        Connection conneccion = null;
        try{
            conneccion = conection.conection();

            try (Statement instruccion = conneccion.createStatement()){
                ResultSet resultado = instruccion.executeQuery("SELECT e.nombre as equipo_nombre, COUNT(h.jugador_dni) as cantidad_jugadores\n" +
                        "FROM equipo e JOIN historial h\n" +
                        "               ON h.cuit_equipo = e.cuit_equipo\n" +
                        "WHERE ('"+fecha+"' BETWEEN h.fecha_inicio AND h.fecha_fin)\n" +
                        "OR ('"+fecha+"' >= h.fecha_inicio and h.fecha_fin IS NULL)\n" +
                        "GROUP BY e.nombre\n" +
                        "ORDER BY e.nombre ASC");

                while (resultado.next()){
                    String nombreEq = resultado.getString("equipo_nombre");
                    Integer cantidadJugadoresEq = resultado.getInt("cantidad_jugadores");

                    System.out.println("El equipo " + nombreEq + " tenia "+ cantidadJugadoresEq + " jugadores en la fecha: " +fecha+".");
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if(conneccion != null){
            try{
                conneccion.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
