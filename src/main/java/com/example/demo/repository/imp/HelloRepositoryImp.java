package com.example.demo.repository.imp;

import com.example.demo.bean.Estudiante;
import com.example.demo.repository.HelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HelloRepositoryImp implements HelloRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public String getDataString() {
        StringBuffer sql = new StringBuffer();
        sql.append("select 'hola mundo db' as columnax");
        String result = null;
        try{
            result = this.jdbcTemplate.queryForObject(sql.toString(), String.class, new Object[]{});
        }catch(EmptyResultDataAccessException e){
            result = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Estudiante getDataObjetc() {
        StringBuffer sql = new StringBuffer();
        sql.append("select 'León 1' as paterno, 'Vilca 2' as materno");
        Estudiante estudiante = null;
        try{
            estudiante = this.jdbcTemplate.queryForObject(sql.toString(), BeanPropertyRowMapper.newInstance(Estudiante.class), new Object[]{});
        }catch(EmptyResultDataAccessException e){
            estudiante = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return estudiante;
    }
}
