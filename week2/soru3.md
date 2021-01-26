
## Soru 3) "sample.pageview": tablosunda 1 gün içerisinde trendyol.com a gelen tüm ziyaretlerin logu var.

```SQL
with task3 as
(
select
     timestamp_trunc(view_ts, minute) as time,
     hll_count.init(deviceid) as deviceid
from `dsmbootcamp.baris_hasdemir.pageview`
group by time
order by time
), temp as
(
select
    time,
    array_agg(deviceid) over(rows between 4 preceding and current row) as active_users_5min,
from task3
)
select
    time,
    hll_count.merge(active_count)
from temp, unnest(active_users_5min) active_count
group by time
order by time;
```

timestamp_trunc ile saniyeler truncate edilir. Dakikaya göre gruplanır. hll_count.init ile device id için sketch oluşturulur. Veri zamana göre sıralanır. son 5 satırdaki device idler array_agg ile aggragate edilir. Daha sonrasında unnest edilen id ler içerisinden hll_count.merge ile farklı id ler saptanır.
