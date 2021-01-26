
# Soru 4) Merge Operasyonu

```SQL
merge `dsmbootcamp.baris_hasdemir.content_category` as t
using `dsmbootcamp.baris_hasdemir.content_category_20201222_00_59` as s
on t.id = s.id
when matched then
  update set t.cdc_date = s.cdc_date, t.category = s.category
when not matched by source then
    update set t.is_deleted = True
when not matched by target and is_deleted = False then
    insert(cdc_date, is_deleted, category)
    values(s.cdc_date, s.is_deleted, s.category)
```

Karşılaştırmak için

```SQL
select 
    count(*) as is_not_equal
from `dsmbootcamp.baris_hasdemir.content_category_20201222_00_59` as s full outer join `dsmbootcamp.baris_hasdemir.content_category_target` as t on s.id = t.id
where farm_fingerprint(to_json_string(s)) != farm_fingerprint(to_json_string(t));
```

is_not_equal = 0
