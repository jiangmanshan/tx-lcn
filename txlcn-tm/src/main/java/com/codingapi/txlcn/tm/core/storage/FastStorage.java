/*
 * Copyright 2017-2019 CodingApi .
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codingapi.txlcn.tm.core.storage;

import com.codingapi.txlcn.common.exception.FastStorageException;
import com.codingapi.txlcn.tm.cluster.TMProperties;

import java.util.List;
import java.util.Set;

/**
 * Description: Manager cache
 * Date: 19-1-21 下午2:53
 *
 * @author ujued
 */
public interface FastStorage {


    /*-----------------------DTX group------------------------------*/

    /**
     * init DTX group.
     * note: group info should clean by self 10 seconds after DTX time.
     *
     * @param groupProps groupProps
     * @throws FastStorageException fastStorageException
     */
    void initGroup(GroupProps groupProps) throws FastStorageException;

    /**
     * the group props.
     *
     * @param groupId groupId
     * @return group props
     * @throws FastStorageException FastStorageException
     */
    GroupProps getGroupProps(String groupId) throws FastStorageException;

    /**
     * DTX group has unit
     *
     * @param groupId         groupId
     * @param transactionUnit transactionUnit
     * @return bool
     */
    boolean containsTransactionUnit(String groupId, TransactionUnit transactionUnit);

    /**
     * has group
     *
     * @param groupId groupId
     * @return bool
     */
    boolean containsGroup(String groupId);

    /**
     * get group all unit
     *
     * @param groupId groupId
     * @return list
     * @throws FastStorageException fastStorageException
     */
    List<TransactionUnit> findTransactionUnitsFromGroup(String groupId) throws FastStorageException;

    /**
     * group join unit
     *
     * @param groupId         groupId
     * @param transactionUnit transactionUnit
     * @throws FastStorageException fastStorageException
     */
    void saveTransactionUnitToGroup(String groupId, TransactionUnit transactionUnit) throws FastStorageException;

    /**
     * clear group
     *
     * @param groupId groupId
     * @throws FastStorageException fastStorageException
     */
    void clearGroup(String groupId) throws FastStorageException;

    /**
     * save DTX state
     * note: transaction state must clean by self 10 seconds after DTX time.
     *
     * @param groupId groupId
     * @param state   status 1 commit 0 rollback
     * @throws FastStorageException fastStorageException
     */
    void saveTransactionState(String groupId, int state) throws FastStorageException;

    /**
     * get DTC state
     *
     * @param groupId groupId
     * @return int
     * @throws FastStorageException fastStorageException
     */
    int getTransactionState(String groupId) throws FastStorageException;

    /**
     * get lock
     * note: lock info should clean by self after DTX time
     *
     * @param contextId contextId
     * @param locks     locks
     * @param lockValue lockValue
     * @throws FastStorageException fastStorageException
     */
    void acquireLocks(String contextId, Set<String> locks, LockValue lockValue) throws FastStorageException;

    /**
     * unlock
     *
     * @param contextId contextId
     * @param locks     locks
     * @throws FastStorageException fastStorageException
     */
    void releaseLocks(String contextId, Set<String> locks) throws FastStorageException;



    /*-----------------------admin token------------------------------*/

    /**
     * save token
     *
     * @param token token
     * @throws FastStorageException fastStorageException
     */
    void saveToken(String token) throws FastStorageException;

    /**
     * find all token
     *
     * @return list
     * @throws FastStorageException fastStorageException
     */
    List<String> findTokens() throws FastStorageException;

    /**
     * delete token
     *
     * @param token token
     * @throws FastStorageException fastStorageException
     */
    void removeToken(String token) throws FastStorageException;



    /*-----------------------Manager------------------------------*/

    /**
     * save Manager address is ip:port
     *
     * @param tmProperties ip:port
     * @throws FastStorageException fastStorageException
     */
    void saveTMProperties(TMProperties tmProperties) throws FastStorageException;

    /**
     * find all Manager
     *
     * @return list
     * @throws FastStorageException fastStorageException
     */
    List<TMProperties> findTMProperties() throws FastStorageException;

    /**
     * delete Manager address
     *
     * @param host            host
     * @param transactionPort transactionPort
     * @throws FastStorageException fastStorageException
     */
    void removeTMProperties(String host, int transactionPort) throws FastStorageException;

    /*-----------------------Machine ID------------------------------*/

    /**
     * 从{@code size}中找一个未使用的数字, 并与{@code key}做关联
     *
     * @param key  key
     * @param size 限制大小
     */
    int acquireMachineId(String key, int size) throws FastStorageException;

    /**
     * 释放所有{@code key}的MachineId
     *
     * @param keys keys
     */
    void releaseMachineIds(List<String> keys);

    /**
     * 获取{@code name}的机器ID
     *
     * @param key key
     * @return machine id
     * @throws FastStorageException FastStorageException
     */
    int getMachineId(String key) throws FastStorageException;
}
