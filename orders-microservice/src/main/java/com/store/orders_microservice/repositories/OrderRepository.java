ckage com.store.orders_microservice.repositories;

import com.store.orders_microservice.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>
{


}
