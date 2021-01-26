```SQL

select 
    name,
    car.id as car_id,
    car.model as car_model,
    manufacture.year as manufacture_year,
    array(select as struct id, timestamp_add(date, interval 3 hour) as date from unnest(purchase)) as purchase
from `dsmbootcamp.baris_hasdemir.semi_structured_hw`
cross join unnest(car) car
cross join unnest(manufacture) as manufacture
on car.id = manufacture.id

```
