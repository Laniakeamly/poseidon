package org.raniaia.poseidon.framework.cache;

import org.raniaia.poseidon.framework.db.NativeResult;

/**
 *
 * 缓存接口.
 *
 * Cache interface.
 *
 * Copyright: Create by tiansheng on 2019/12/9 23:10
 *
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
public interface PoseidonCache {

    /**
     * 获取缓存
     * @param sql
     * @param args
     * @return
     */
    NativeResult get(String sql, Object... args);

    /**
     * 保存
     * @param sql
     * @param result
     * @param args
     * @return
     */
    void save(String sql, NativeResult result, Object... args);

    /**
     * 刷新缓存
     * @param sql
     */
    void refresh(String sql);

    /**
     * 刷新所有缓存
     */
    void refreshAll();

}
