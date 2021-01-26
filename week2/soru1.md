## Soru 1) 1980’den itibaren spor grubu bazında en çok madalya alan 1. 3. 5. ülkeyi bulalım.

```SQL

with task1 as
(
    select
        sport,
        country,
        count(*) as medal_count,
        row_number() over(partition by sport order by count(*) desc) as row_number
    from `dsmbootcamp.baris_hasdemir.summer_medals`
    where year >= 1980 and country is not null
    group by sport, Country
), temp as
(
select
    sport,
    lead(country,0) over(partition by sport order by medal_count desc) as first_champ,
    lead(country,1) over(partition by sport order by medal_count desc) as third_champ,
    lead(country,2) over(partition by sport order by medal_count desc) as fifth_champ,
from task1
where row_number in (1,3,5)
)
select 
    *
from temp
where first_champ is not null and third_champ is not null and fifth_champ is not null;

```
