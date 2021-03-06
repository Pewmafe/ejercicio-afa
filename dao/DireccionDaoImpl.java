package ejercicio.dao;

import ejercicio.Conexion;
import ejercicio.model.Direccion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DireccionDaoImpl implements  DireccionDao{
    private Conexion conection = new Conexion();

    @Override
    public Integer insert(Direccion direccion) {
        Connection conneccion = null;
        Integer resultado=0;
        try{
            conneccion = conection.conection();

            try (Statement instruccion = conneccion.createStatement()){
                String dirCalle = direccion.getCalle();
                Integer dirNro = direccion.getNumero();
                String dirLocalidad = direccion.getLocalidad();
                String dirProvincia = direccion.getProvincia();

                String sql = "INSERT INTO direccion(calle, nro, localidad, provincia) VALUES('"+dirCalle+"',"+dirNro+",'"
                        +dirLocalidad+"','"+dirProvincia+"');";
                instruccion.execute(sql);
                ResultSet resultSet = instruccion.executeQuery("SELECT LAST_INSERT_ID();");
                resultSet.next();
                resultado = resultSet.getInt(1);

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
        return resultado;
    }

    @Override
    public void update(Direccion direccion) {
        Connection conneccion = null;
        try{
            conneccion = conection.conection();

            try (Statement instruccion = conneccion.createStatement()){
                String dirCalle = direccion.getCalle();
                Integer dirNro = direccion.getNumero();
                String dirLocalidad = direccion.getLocalidad();
                String dirProvincia = direccion.getProvincia();
                Integer id = this.obtenerIdDireccion(direccion);

                String sql = "UPDATE direccion SET calle = '" + dirCalle +
                        "' , nro = " + dirNro +
                        " , provincia = '" + dirProvincia +
                        "' , localidad = '" + dirLocalidad +
                        "' WHERE id_direccion = " + id;
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
                ResultSet resultado = instruccion.executeQuery("SELECT * FROM direccion;");
                List<Direccion> direcciones= new ArrayList<>();
                while (resultado.next()) {
                    String dirCalle = resultado.getString("calle");
                    Integer dirNro = resultado.getInt("nro");
                    String dirLocalidad = resultado.getString("localidad");
                    String dirProvincia = resultado.getString("provincia");


                    Direccion direccion = new Direccion(dirCalle, dirNro,
                            dirLocalidad, dirProvincia);

                    direcciones.add(direccion);
                }

                for (Direccion direccion: direcciones) {
                    System.out.println(direccion.toString());
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
    public void delete(Integer id) {
        Connection conneccion = null;
        try{
            conneccion = conection.conection();

            try (Statement instruccion = conneccion.createStatement()){

                String sql =  "DELETE FROM direccion WHERE direccion.id_direccion = "+ id +";";
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
    public Integer obtenerIdDireccion(Direccion direccion) {
        Connection conneccion = null;
        Integer resultado=0;
        try{
            conneccion = conection.conection();

            try (Statement instruccion = conneccion.createStatement()){
                String dirCalle = direccion.getCalle();
                Integer dirNro = direccion.getNumero();
                String dirLocalidad = direccion.getLocalidad();
                String dirProvincia = direccion.getProvincia();

                String sql = "SELECT id_direccion FROM direccion WHERE calle LIKE '"+dirCalle+"' AND nro LIKE "+dirNro+" " +
                        " AND localidad LIKE '"+dirLocalidad+"'AND provincia LIKE '"+dirProvincia+"'";
                ResultSet resultSet = instruccion.executeQuery(sql);
                if(resultSet.next()){
                    resultado = resultSet.getInt("id_direccion");
                }else{
                    resultado = this.insert(direccion);
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
        return resultado;
    }
}
