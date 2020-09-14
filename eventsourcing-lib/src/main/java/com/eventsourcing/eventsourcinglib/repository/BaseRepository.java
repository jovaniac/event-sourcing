/**
 * 
 */
package com.eventsourcing.eventsourcinglib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author kaihe
 *
 */

@NoRepositoryBean
public interface BaseRepository<T, PK> extends JpaRepository<T, PK> {

}

