## Soru 2) 1980’den itibaren herhangi bir spor grubunda üst üste 3 veya daha fazla madalya almış atletleri bulalım.

```SQL
with task2 as
(
select
    year,
    athlete,
    count(*) over(partition by athlete order by year rows between unbounded preceding and unbounded following) count_year,
    lead(year,0) over(partition by athlete order by year) as first,
    lead(year,1) over(partition by athlete order by year) as second,
    lead(year,2) over(partition by athlete order by year) as third,
from `dsmbootcamp.baris_hasdemir.summer_medals`
where year >= 1980
)
select 
    athlete
from task2
where count_year >= 3 and third - second = 4 and second - first = 4;
```
