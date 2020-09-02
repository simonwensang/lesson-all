select ( select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 21
and role_type = 1
and create_time >= '2019-06-11 00:00:00'
and create_time <= '2019-06-11 23:59:59') as startGroup21,

(select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 21
and role_type = 2
and create_time >= '2019-06-11 00:00:00'
and create_time <= '2019-06-11 23:59:59') as joinGroup21  ,

(select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 21
and group_status = 2
and update_time >= '2019-06-11 00:00:00'
and update_time <= '2019-06-11 23:59:59') as succeed21,

( select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 22
and role_type = 1
and create_time >= '2019-06-11 00:00:00'
and create_time <= '2019-06-11 23:59:59') as startGroup22,

(select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 22
and role_type = 2
and create_time >= '2019-06-11 00:00:00'
and create_time <= '2019-06-11 23:59:59') as joinGroup22  ,

(select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 22
and group_status = 2
and update_time >= '2019-06-11 00:00:00'
and update_time <= '2019-06-11 23:59:59') as succeed22,

( select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 23
and role_type = 1
and create_time >= '2019-06-11 00:00:00'
and create_time <= '2019-06-11 23:59:59') as startGroup23,

(select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 23
and role_type = 2
and create_time >= '2019-06-11 00:00:00'
and create_time <= '2019-06-11 23:59:59') as joinGroup23  ,

(select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 23
and group_status = 2
and update_time >= '2019-06-11 00:00:00'
and update_time <= '2019-06-11 23:59:59') as succeed23 ,

( select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id =  24
and role_type = 1
and create_time >= '2019-06-11 00:00:00'
and create_time <= '2019-06-11 23:59:59') as startGroup24,

(select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 24
and role_type = 2
and create_time >= '2019-06-11 00:00:00'
and create_time <= '2019-06-11 23:59:59') as joinGroup24  ,

(select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 24
and group_status = 2
and update_time >= '2019-06-11 00:00:00'
and update_time <= '2019-06-11 23:59:59') as succeed24,

( select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id =25
and role_type = 1
and create_time >= '2019-06-11 00:00:00'
and create_time <= '2019-06-11 23:59:59') as startGroup25,

(select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 25
and role_type = 2
and create_time >= '2019-06-11 00:00:00'
and create_time <= '2019-06-11 23:59:59') as joinGroup25  ,

(select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 25
and group_status = 2
and update_time >= '2019-06-11 00:00:00'
and update_time <= '2019-06-11 23:59:59') as succeed25,

( select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 26
and role_type = 1
and create_time >= '2019-06-11 00:00:00'
and create_time <= '2019-06-11 23:59:59') as startGroup26,

(select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 26
and role_type = 2
and create_time >= '2019-06-11 00:00:00'
and create_time <= '2019-06-11 23:59:59') as joinGroup26  ,

(select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 26
and group_status = 2
and update_time >= '2019-06-11 00:00:00'
and update_time <= '2019-06-11 23:59:59') as succeed26 ,

( select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 27
and role_type = 1
and create_time >= '2019-06-11 00:00:00'
and create_time <= '2019-06-11 23:59:59') as startGroup27,

(select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 27
and role_type = 2
and create_time >= '2019-06-11 00:00:00'
and create_time <= '2019-06-11 23:59:59') as joinGroup27  ,

(select count(1) from ssc_user_group_booking
where activity_id = 9
and group_booking_id = 27
and group_status = 2
and update_time >= '2019-06-11 00:00:00'
and update_time <= '2019-06-11 23:59:59') as succeed27
