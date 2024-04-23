package com.example.AuthService.repository;

import com.example.AuthService.domain.dto.UserDTO;
import com.example.AuthService.utils.DataUtils;
import jakarta.persistence.*;


import java.util.*;

public class UserRepositoryImpl implements UserCustomRepository{
    @PersistenceContext
    private EntityManager em;
    @Override
    public List<UserDTO> findUserByEmail(String email,String status) {
        StringBuilder sqlQuery = new StringBuilder();
        Map<String, Object> mapParam = new HashMap<>();
        List<UserDTO> userDTOList = new ArrayList<>();

        sqlQuery.append( "SELECT id, username,email,password,status FROM user ");
        sqlQuery.append(" WHERE 1 = 1");
        if(!DataUtils.isNullOrEmpty(email)){
            sqlQuery.append(" AND email = :email");
            mapParam.put("email",email);
        }
        if(!DataUtils.isNullOrEmpty(status)){
            sqlQuery.append(" AND status = :status");
            mapParam.put("status",status);
        }
        if(mapParam.size() == 0){
            sqlQuery.append( " AND 1=0 ");
        }
        sqlQuery.append( " LIMIT 1 ");
        Query query = em.createNativeQuery(sqlQuery.toString());
        mapParam.forEach(query::setParameter);
        List<Object[]> resultList = query.getResultList();

        UserDTO userDTO;
        int i;
        if(!resultList.isEmpty()){
            for(Object[] o: resultList){
                i= 0;
                userDTO = new UserDTO();
                userDTO.setId((Long.parseLong(String.valueOf(o[i++]))));
                userDTO.setUserName(String.valueOf(o[i++]));
                userDTO.setEmail(String.valueOf(o[i++]));
                userDTO.setPassword(String.valueOf(o[i++]));
                userDTO.setStatus(String.valueOf(o[i]));
                userDTOList.add(userDTO);
               break;
            }
        }
        return userDTOList;
    }
}
