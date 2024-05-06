package com.example.AuthService.repository;

import com.example.AuthService.domain.dto.KeyTokenDTO;
import com.example.AuthService.domain.dto.UserDTO;
import com.example.AuthService.utils.DataUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyTokenRepositoryImpl implements KeyTokenCustomRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<KeyTokenDTO> findKeyTokenByUserId(Long userId, String status) {
        StringBuilder sqlQuery = new StringBuilder();
        Map<String, Object> mapParam = new HashMap<>();
        List<KeyTokenDTO> keyTokenDTOList = new ArrayList<>();

        sqlQuery.append(" SELECT k.id, k.user_id,k.public_key,k.private_key,k.refresh_token, k.status ");
        sqlQuery.append(" FROM key_token k ");
        sqlQuery.append(" WHERE 1 = 1");

        if (userId != null) {
            sqlQuery.append(" AND k.user_id = :userId");
            mapParam.put("userId", userId);
        }
        if (!DataUtils.isNullOrEmpty(status)) {
            sqlQuery.append(" AND k.status = :status");
            mapParam.put("status", status);
        }

        if (mapParam.isEmpty()) {
            sqlQuery.append(" AND 1=0 ");
        }

        Query query = em.createNativeQuery(sqlQuery.toString());
        mapParam.forEach(query::setParameter);
        List<Object[]> resultList = query.getResultList();

        KeyTokenDTO dto;
        int i;
        if (!resultList.isEmpty()) {
            for (Object[] o : resultList) {
                i = 0;
                dto = new KeyTokenDTO();
                dto.setId((Long.parseLong(String.valueOf(o[i++]))));
                dto.setUserId((Long.parseLong(String.valueOf(o[i++]))));
                dto.setPublicKey(String.valueOf(o[i++]));
                dto.setPrivateKey(String.valueOf(o[i++]));
                dto.setRefreshToken(String.valueOf(o[i++]));
                dto.setStatus(String.valueOf(o[i]));
                keyTokenDTOList.add(dto);
                break;
            }
        }

        return keyTokenDTOList;
    }
}
