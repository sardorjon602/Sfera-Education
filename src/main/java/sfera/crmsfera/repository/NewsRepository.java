package sfera.crmsfera.repository;

import sfera.crmsfera.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Long> {
}
