create table s_user(
	actorno nvarchar(8) not null comment '用户码',
	enname nvarchar(32) null comment '选项值',
	actorname nvarchar(20) null comment '用户名称',
	nickname nvarchar(40) null comment '昵称',
	state char(1) null comment '状态',
	password nvarchar(32) null comment '密码',
	startdate char(10) null comment '启用日期',
	passwvalda char(10) null comment '密码',
	firedate char(10) null comment '解雇日期',
	birthday char(10) null comment '生日',
	telnum nvarchar(20) null comment '联系电话',
	idcardno nvarchar(20) null comment '身份证号码',
	allowopersys char(20) null comment '允许操作的系统',
	lastlogdat char(10) null comment '最后登陆日期',
	creater nvarchar(40) null comment '创建人',
	creattime char(10) null comment '创建时间',
	usermail nvarchar(50) null comment '邮箱',
	wrongpinnum decimal(22) null comment '密码输入错误次数',
	isadmin char(1) null comment '是否管理员',
	memo nvarchar(200) null comment '备注',
	ipmask nvarchar(40) null comment '用户ip掩码',
	orderno decimal(22) null comment '排序字段',
	question nvarchar(200) null comment '用户防伪问题',
	answer nvarchar(200) null comment '用户防伪答案',
	orgid nvarchar(16) null comment '组织号',
	depno nvarchar(16) null comment '部门编号',
	session_id nvarchar(40) null comment '当前登录会话id',
	rank char(2) null comment '职级',
	parentactorno nvarchar(8) null comment '主账号用户码',
	device_no nvarchar(18) null comment '未知',
	menu_config longblob null comment '未知',
	is_priority_show char(1) null comment '是否优先显示',
	if_together_wf char(1) null comment '是否合并待办',
	op_model char(1) null comment '操作模式',
	mob_num nvarchar(20) null comment '手机号码',
	primary key (actorno)
) engine=innodb default charset=utf8 comment='用户管理';
alter table s_user comment='用户管理';
create table s_param_info(
	param_code varchar(32) not null comment '参数代码',
	param_name varchar(64) null comment '参数名称',
	param_value varchar(512) null comment '参数值',
	param_scope char(1) null comment '参数使用范围',
	subs_code varchar(16) null comment '子系统代码',
	plugin_id int null comment '应用插件id',
	remark varchar(254) null comment '备注',
	primary key (param_code)
) engine=innodb default charset=utf8 comment='01.平台参数信息表';
alter table s_param_info comment='01.平台参数信息表';

create table s_dic(
	enname nvarchar(20) not null comment '选项值',
	cnname nvarchar(100) null comment '选项名称',
	opttype nvarchar(30) not null comment '选项类别',
	memo nvarchar(100) null comment '选项描述',
	flag nvarchar(3) null comment '标志',
	levels nvarchar(2) null comment '级别',
	orderid decimal(38) null comment '排序字段',
	modify_date nvarchar(10) null comment '修改日期',
	primary key (enname,opttype)
) engine=innodb default charset=utf8 comment='通用字典';
alter table s_dic comment='通用字典';
create table s_inst_srvs_conf(
	srvs_inst_id varchar(48) not null comment '服务组件实例标识',
	srvs_code varchar(16) null comment '服务组件代码',
	sys_inst_id varchar(48) null comment '系统实例标识',
	is_allow_service char(1) null comment '是否允许运行',
	rsv_flag char(1) null comment '保留字段(标志)',
	primary key (srvs_inst_id)
) engine=innodb default charset=utf8 comment='02.服务实例配置信息';
alter table s_inst_srvs_conf comment='02.服务实例配置信息';
create table s_inst_license(
	host_name varchar(24) null comment '主机名/ip地址',
	web_port varchar(5) null comment 'web端口',
	sys_inst_id varchar(48) not null comment '系统实例标识',
	sys_code varchar(24) null comment '系统代码',
	sys_name varchar(80) null comment '系统名称',
	auth_target varchar(80) null comment '授权目标',
	auth_begin_date char(10) null comment '授权起始日期',
	auth_end_date char(10) null comment '授权截止日期',
	remark varchar(254) null comment '备注',
	primary key (sys_inst_id)
) engine=innodb default charset=utf8 comment='01.系统注册授权信息';
alter table s_inst_license comment='01.系统注册授权信息';

create table s_subs_info(
	subs_code varchar(16) not null comment '子系统代码',
	subs_name varchar(80) null comment '子系统名称',
	subs_data_date char(10) null comment '子系统数据日期',
	subs_last_data_date char(10) null comment '子系统上一数据日期',
	subs_load_date char(10) null comment '子系统装数完成日期',
	subs_bat_date char(10) null comment '子系统批次完成日期',
	batch_id_prefix varchar(16) null comment '批次编号前缀',
	rsv_date char(10) null comment '备用日期',
	rsv_state char(2) null comment '备用状态',
	primary key (subs_code)
) engine=innodb default charset=utf8 comment='01.平台子系统基础信息表';
alter table s_subs_info comment='01.平台子系统基础信息表';

create table s_subs_datasource(
	subs_code varchar(16) not null comment '子系统代码',
	subs_ds_code varchar(40) not null comment '子系统数据源代码',
	subs_ds_name varchar(80) null comment '子系统数据源名称',
	ds_conn_str varchar(128) null comment '数据源-连接字符串',
	ds_user_id varchar(16) null comment '数据源-用户名',
	ds_user_pwd varchar(64) null comment '数据源-用户密码',
	ds_schema_name varchar(16) null comment '数据源-模式',
	ds_db_name varchar(32) null comment '数据源-数据库名',
	subs_ds_type char(2) null comment '子系统数据源类型',
	primary key (subs_code,subs_ds_code)
) engine=innodb default charset=utf8 comment='03.平台子系统数据源信息表';
alter table s_subs_datasource comment='03.平台子系统数据源信息表';

create table s_subs_tables(
	subs_code varchar(16) not null comment '子系统代码',
	tab_name varchar(40) null comment '表名称',
	remark varchar(254) null comment '备注',
	valid_date char(10) null comment '生效日期',
	invalid_date char(10) null comment '失效日期',
	primary key (subs_code)
) engine=innodb default charset=utf8 comment='02.平台子系统所需表信息';
alter table s_subs_tables comment='02.平台子系统所需表信息';

create table s_org(
	organno nvarchar(20) null comment '机构码',
	suporganno nvarchar(20) null comment '上级机构码',
	locate nvarchar(100) null comment '所属地区',
	organname nvarchar(40) null comment '机构名称',
	organshortform nvarchar(40) null comment '机构简称',
	enname nvarchar(40) null comment '选项值',
	orderno decimal(38) null comment '排序字段',
	distno nvarchar(12) null comment '地区编号',
	launchdate nvarchar(10) null comment '开办日期',
	organlevel decimal(38) null comment '机构级别',
	fincode nvarchar(21) null comment '金融代码',
	state decimal(38) null comment '状态',
	organchief nvarchar(32) null comment '机构负责人',
	telnum nvarchar(20) null comment '联系电话',
	address nvarchar(200) null comment '地址',
	postcode nvarchar(10) null comment '邮编',
	control nvarchar(10) null comment '控制字',
	arti_organno nvarchar(20) null comment '所属法人机构码',
	distname nvarchar(100) null comment '地区名称',
	area_dev_cate_type nvarchar(1) null comment '地区发展分类',
	is_marketing nvarchar(1) null comment '是否营销中心',
	busiorganno nvarchar(20) null comment '业务机构编号'
) engine=innodb default charset=utf8 comment='机构管理';
alter table s_org comment='机构管理';

create table s_srvs_cron_inst(
	job_code varchar(40) not null comment '定时作业编号',
	job_desc nvarchar(80) null comment '定时作业描述',
	state int null comment '状态',
	start_time varchar(24) null comment '启动时间',
	end_time varchar(24) null comment '结束时间',
	cost_time decimal(10,2) null comment '耗时(秒)',
	agent_id nvarchar(32) null comment '代理节点标识',
	result_desc nvarchar(4000) null comment '结果描述',
	primary key (job_code)
) engine=innodb default charset=utf8 comment='定时调度实例';
alter table s_srvs_cron_inst comment='定时调度实例';

create table s_srvs_cron_conf(
	job_code varchar(40) not null comment '定时作业编号',
	job_desc varchar(80) null comment '定时作业描述',
	job_class_type varchar(16) null comment '作业类类型',
	job_class varchar(128) null comment '作业实现类',
	job_method varchar(60) null comment '作业类方法',
	cron_expression varchar(32) null comment 'cron表达式',
	again_time int null comment '失败重试次数',
	retry_second int null comment '重试间隔秒数',
	agent_id nvarchar(32) null comment '代理节点标识',
	remark varchar(254) null comment '备注',
	primary key (job_code)
) engine=innodb default charset=utf8 comment='定时调度配置';
alter table s_srvs_cron_conf comment='定时调度配置';

create table s_srvs_cron_his(
	record_id char(32) not null comment '记录id',
	job_code varchar(40) null comment '定时作业编号',
	job_desc nvarchar(80) null comment '定时作业描述',
	state int null comment '状态',
	start_time varchar(24) null comment '启动时间',
	end_time varchar(24) null comment '结束时间',
	cost_time decimal(10,2) null comment '耗时(秒)',
	agent_id nvarchar(32) null comment '代理节点标识',
	result_desc nvarchar(4000) null comment '结果描述',
	primary key (record_id)
) engine=innodb default charset=utf8 comment='定时调度历史';
alter table s_srvs_cron_his comment='定时调度历史';

create table s_agent_info(
	agent_id nvarchar(32) not null comment '代理节点标识',
	agent_name nvarchar(80) null comment '代理节点名称',
	agent_state char(1) null comment '代理节点状态',
	agent_url varchar(128) null comment '代理节点地址',
	start_time varchar(24) null comment '启动时间',
	stop_time varchar(24) null comment '停止时间',
	primary key (agent_id)
) engine=innodb default charset=utf8 comment='代理节点信息';
alter table s_agent_info comment='代理节点信息';

create table s_srvs_admin_log(
	srvs_inst_id varchar(48) null comment '服务组件实例标识',
	srvs_inst_action nchar(2) null comment '实例操作类型',
	action_time varchar(24) null comment '操作时间',
	action_result char(1) null comment '操作结果',
	action_detail_info varchar(4000) null comment '操作详情',
	serial_no varchar(32) not null comment '流水号',
	primary key (serial_no)
) engine=innodb default charset=utf8 comment='02.服务管理日志';
alter table s_srvs_admin_log comment='02.服务管理日志';
create table s_srvs_called_log(
	client_ip varchar(24) null comment '客户端ip地址',
	client_userid varchar(20) null comment '客户端用户',
	srvs_inst_id varchar(48) null comment '服务组件实例标识',
	srvs_log_type varchar(16) null comment '服务日志类型',
	srvs_busi_code varchar(16) null comment '交易代码',
	cost_time decimal(10,2) null comment '执行时长（秒）',
	start_time varchar(24) null comment '启动时间',
	stop_time varchar(24) null comment '停止时间',
	in_buffer varchar(2048) null comment '输入数据',
	out_buffer varchar(2048) null comment '输出数据',
	srvs_busi_result char(1) null comment '交易结果',
	serial_no varchar(32) not null comment '流水号',
	primary key (serial_no)
) engine=innodb default charset=utf8 comment='03.服务调用日志';
alter table s_srvs_called_log comment='03.服务调用日志';
create table bat_batch_stage_config(
	stage_id varchar(24) not null comment '阶段编号',
	stage_name varchar(40) null comment '阶段名称',
	batch_id varchar(24) not null comment '批次标识',
	remark varchar(254) null comment '备注',
	primary key (stage_id,batch_id)
) engine=innodb default charset=utf8 comment='01.批次任务阶段';
alter table bat_batch_stage_config comment='01.批次任务阶段';
create table bat_task_locale_config(
	locale_id varchar(24) not null comment '执行场所编号',
	locale_name varchar(40) null comment '执行场所名称',
	locale_type char(1) null comment '场所类型',
	locale_ip varchar(24) null comment '场所ip',
	locale_port varchar(8) null comment '场所端口',
	login_type char(1) null comment '登录方式',
	login_user varchar(24) null comment '登录用户名',
	login_pwd varchar(40) null comment '登录密码',
	remark varchar(254) null comment '备注',
	primary key (locale_id)
) engine=innodb default charset=utf8 comment='02.执行场所配置';
alter table bat_task_locale_config comment='02.执行场所配置';

create table bat_batch_info_config(
	batch_id varchar(24) not null comment '批次标识',
	batch_name varchar(40) null comment '批次名称',
	subs_code varchar(16) null comment '子系统代码',
	launch_type char(1) null comment '发起方式',
	batch_cron_value varchar(40) null comment '定时周期',
	valid_date char(10) null comment '生效日期',
	equally_task_amount int null comment '任务并发数',
	is_run_again char(1) null comment '是否允许重跑',
	agent_id nvarchar(32) null comment '代理节点标识',
	remark varchar(254) null comment '备注',
	primary key (batch_id)
) engine=innodb default charset=utf8 comment='批次信息配置';
alter table bat_batch_info_config comment='批次信息配置'


create table bat_task_unit_config(
	task_name nvarchar(64) null comment '任务名称',
	task_id varchar(24) not null comment '任务编号',
	previous_task_id varchar(24) null comment '前一任务编号',
	batch_id varchar(24) null comment '批次标识',
	stage_id varchar(24) null comment '阶段编号',
	locale_id varchar(24) null comment '执行场所编号',
	plugin_id int null comment '应用插件id',
	plugin_source_type char(1) null comment '插件配置数据来源方式',
	plugin_para_flag varchar(40) null comment '插件配置数据标识',
	task_pri char(1) null comment '任务优先级',
	task_run_type char(1) null comment '任务执行类型',
	task_cycle_type char(1) null comment '任务周期类型',
	task_cron_value varchar(40) null comment '任务定时周期',
	task_delay_time int null comment '任务延时执行时间(秒)',
	task_skip_tactic char(1) null comment '任务失败跳过策略',
	again_run_space int null comment '任务重复调起时间间隔(秒)',
	task_estimate_time int null comment '任务预计执行时间(秒)',
	task_use_state char(1) null comment '任务使用状态',
	remark varchar(254) null comment '备注',
	max_run_count int null comment '任务最大重复调起次数',
	task_timeout_time int null comment '任务运行超时时间(秒)',
	task_timeout_tactic char(1) null comment '任务运行超时策略',
	subs_ds_code varchar(40) null comment '子系统数据源代码',
	task_use_area char(1) null comment '任务使用场景',
	plugin_type char(1) null comment '应用插件类型',
	max_wait_time int null comment '最长等待时间',
	cycle_inteval int null comment '轮询间隔(秒)',
	primary key (task_id)
) engine=innodb default charset=utf8 comment='02.批次任务配置';
alter table bat_task_unit_config comment='02.批次任务配置';
create table bat_inst_batch(
	batch_id varchar(24) not null comment '批次标识',
	batch_order int null comment '批次序号',
	batch_name varchar(40) null comment '批次名称',
	batch_state char(1) null comment '批次状态',
	batch_intervene_state char(1) null comment '干预状态',
	batch_date char(10) null comment '批次日期',
	start_time varchar(24) null comment '启动时间',
	end_time varchar(24) null comment '结束时间',
	need_run_count int null comment '需调度任务数',
	exist_run_count int null comment '运行中任务数',
	not_run_count int null comment '未运行任务数',
	succeed_run_count int null comment '运行成功任务数',
	faild_run_count int null comment '运行失败任务数',
	warn_run_count int null comment '运行警告任务数',
	skip_run_count int null comment '运行置过任务数',
	batch_serial_no varchar(32) null comment '批次流水号',
	remark varchar(254) null comment '备注',
	subs_code varchar(16) null comment '子系统代码',
	cost_time decimal(10,2) null comment '运行时长',
	stage_name varchar(40) null comment '阶段名称',
	stage_id varchar(24) null comment '阶段编号',
	primary key (batch_id)
) engine=innodb default charset=utf8 comment='01.批次实例信息';
alter table bat_inst_batch comment='01.批次实例信息';
create table bat_inst_task(
	batch_id varchar(24) not null comment '批次标识',
	batch_order int not null comment '批次序号',
	task_id varchar(24) not null comment '任务编号',
	task_name nvarchar(64) null comment '任务名称',
	stage_id varchar(24) null comment '阶段编号',
	task_run_state char(1) null comment '任务执行状态',
	task_intervene_state char(1) null comment '任务干预状态',
	cost_time decimal(10,2) null comment '运行时长',
	start_time varchar(24) null comment '启动时间',
	end_time varchar(24) null comment '结束时间',
	warn_count int null comment '警告次数',
	other varchar(40) null comment '其它',
	batch_serial_no varchar(32) null comment '批次流水号',
	state_desc varchar(254) null comment '状态描述',
	batch_date char(10) null comment '批次日期',
	stage_name varchar(40) null comment '阶段名称',
	primary key (batch_id,batch_order,task_id)
) engine=innodb default charset=utf8 comment='02.批次任务实例';
alter table bat_inst_task comment='02.批次任务实例';
create table bat_inst_batch_h(
	batch_id varchar(24) null comment '批次标识',
	batch_order int null comment '批次序号',
	batch_name varchar(40) null comment '批次名称',
	subs_code varchar(16) null comment '子系统代码',
	batch_state char(1) null comment '批次状态',
	batch_intervene_state char(1) null comment '干预状态',
	stage_id varchar(24) null comment '阶段编号',
	stage_name varchar(40) null comment '阶段名称',
	batch_date char(10) null comment '批次日期',
	start_time varchar(24) null comment '启动时间',
	end_time varchar(24) null comment '结束时间',
	cost_time decimal(10,2) null comment '运行时长',
	need_run_count int null comment '需调度任务数',
	exist_run_count int null comment '运行中任务数',
	not_run_count int null comment '未运行任务数',
	succeed_run_count int null comment '运行成功任务数',
	faild_run_count int null comment '运行失败任务数',
	warn_run_count int null comment '运行警告任务数',
	skip_run_count int null comment '运行置过任务数',
	batch_serial_no varchar(32) not null comment '批次流水号',
	remark varchar(254) null comment '备注',
	primary key (batch_serial_no)
) engine=innodb default charset=utf8 comment='01.批次历史信息';
alter table bat_inst_batch_h comment='01.批次历史信息';
create table bat_inst_task_h(
	batch_id varchar(24) null comment '批次标识',
	batch_order int null comment '批次序号',
	task_id varchar(24) not null comment '任务编号',
	batch_date char(10) null comment '批次日期',
	task_name nvarchar(64) null comment '任务名称',
	stage_id varchar(24) null comment '阶段编号',
	stage_name varchar(40) null comment '阶段名称',
	task_run_state char(1) null comment '任务执行状态',
	task_intervene_state char(1) null comment '任务干预状态',
	start_time varchar(24) null comment '启动时间',
	end_time varchar(24) null comment '结束时间',
	cost_time decimal(10,2) null comment '运行时长',
	warn_count int null comment '警告次数',
	other varchar(40) null comment '其它',
	batch_serial_no varchar(32) not null comment '批次流水号',
	state_desc varchar(254) null comment '状态描述',
	primary key (task_id,batch_serial_no)
) engine=innodb default charset=utf8 comment='02.批次任务历史';
alter table bat_inst_task_h comment='02.批次任务历史';
create table plugin_sysupd_conf(
	plugin_conf_id varchar(24) not null comment '配置标识符',
	conf_sort int not null comment '配置顺序',
	sysupd_action char(2) null comment '系统信息更新动作',
	primary key (plugin_conf_id,conf_sort)
) engine=innodb default charset=utf8 comment='01.系统信息更新配置表';
alter table plugin_sysupd_conf comment='01.系统信息更新配置表';
create table plugin_reform_conf(
	plugin_conf_id varchar(24) not null comment '配置标识符',
	conf_sort int not null comment '配置顺序',
	reform_table_schema varchar(24) null comment '表所属模式',
	reform_table_name varchar(38) null comment '重整表名称',
	reform_group_id varchar(24) null comment '重整组id',
	reform_cycle_day int null comment '重整周期',
	last_reform_date char(10) null comment '上次重整日期',
	rsv_option varchar(24) null comment '备用选项',
	valid_date char(10) null comment '生效日期',
	invalid_date char(10) null comment '失效日期',
	last_modify_date char(10) null comment '最后更新日期',
	primary key (plugin_conf_id,conf_sort)
) engine=innodb default charset=utf8 comment='01.数据表重整配置表';
alter table plugin_reform_conf comment='01.数据表重整配置表';
create table plugin_excel_conf(
	plugin_conf_id varchar(24) not null comment '配置标识符',
	conf_sort int not null comment '配置顺序',
	excel_oper_type char(2) null comment 'excel操作类型',
	param_key_value varchar(512) null comment '参数键值对',
	configure_file varchar(128) null comment 'excel操作配置文件',
	faild_deal char(1) null comment '失败处理方式',
	last_modify_date char(10) null comment '最后更新日期',
	primary key (plugin_conf_id,conf_sort)
) engine=innodb default charset=utf8 comment='01.excel操作配置表';
alter table plugin_excel_conf comment='01.excel操作配置表';
create table plugin_check_conf(
	plugin_conf_id varchar(24) not null comment '配置标识符',
	conf_sort int not null comment '配置顺序',
	check_item_name nvarchar(64) null comment '检查项名称',
	check_item_sql varchar(2000) null comment '检查项sql',
	check_suc_condition nvarchar(254) null comment '检查成功条件',
	check_err_desc nvarchar(254) null comment '错误描述',
	check_faild_deal char(1) null comment '检查失败处理方式',
	valid_date char(10) null comment '生效日期',
	invalid_date char(10) null comment '失效日期',
	last_modify_date char(10) null comment '最后更新日期',
	primary key (plugin_conf_id,conf_sort)
) engine=innodb default charset=utf8 comment='01.数据检查配置表';
alter table plugin_check_conf comment='01.数据检查配置表';
create table plugin_wt_conf(
	plugin_conf_id varchar(24) not null comment '配置标识符',
	check_inteval int null comment '等待间隔',
	max_wait_time int null comment '最长等待时间',
	wait_desc varchar(80) null comment '等待描述',
	primary key (plugin_conf_id)
) engine=innodb default charset=utf8 comment='01.等待指定条件配置表';
alter table plugin_wt_conf comment='01.等待指定条件配置表';
create table plugin_wt_condition(
	plugin_conf_id varchar(24) not null comment '配置标识符',
	conf_sort int not null comment '配置顺序',
	check_item_name nvarchar(64) null comment '检查项名称',
	check_item_sql varchar(2000) null comment '检查项sql',
	check_suc_condition nvarchar(254) null comment '检查成功条件',
	valid_date char(10) null comment '生效日期',
	invalid_date char(10) null comment '失效日期',
	last_modify_date char(10) null comment '最后更新日期',
	primary key (plugin_conf_id,conf_sort)
) engine=innodb default charset=utf8 comment='02.等待条件表';
alter table plugin_wt_condition comment='02.等待条件表';
create table plugin_fileop_conf(
	plugin_conf_id varchar(24) not null comment '配置标识符',
	conf_sort int not null comment '配置顺序',
	file_op char(2) null comment '文件操作',
	op_cycle_day int null comment '操作周期',
	last_op_date char(10) null comment '上次操作日期',
	last_modify_date char(10) null comment '最后更新日期',
	distance_day int null comment '目标距离(天)',
	file_target varchar(128) null comment '目标文件',
	primary key (plugin_conf_id,conf_sort)
) engine=innodb default charset=utf8 comment='01.文件操作配置';
alter table plugin_fileop_conf comment='01.文件操作配置';
create table plugin_export_conf(
	plugin_conf_id varchar(24) not null comment '配置标识符',
	conf_sort int not null comment '配置顺序',
	export_mode char(2) null comment '数据导出方式',
	export_target varchar(512) null comment '数据表/sql语句',
	export_to_file varchar(100) null comment '文件存放路径',
	field_separator varchar(16) null comment '字段分隔符',
	file_charset varchar(16) null comment '文件编码格式',
	primary key (plugin_conf_id,conf_sort)
) engine=innodb default charset=utf8 comment='01.导出为文本文件插件';
alter table plugin_export_conf comment='01.导出为文本文件插件';
create table plugin_load_conf(
	table_name varchar(38) not null comment '数据库表名',
	table_cnname varchar(80) null comment '表中文名称',
	table_type char(1) null comment '数据表类型',
	table_load_mode char(1) null comment '数据装载方式',
	up_sysname varchar(80) null comment '供数系统名称',
	load_from_file varchar(100) null comment '文件路径',
	file_row_flag char(1) null comment '是否允许条数为零',
	load_warn_flag char(1) null comment '是否忽略警告',
	diff_comp_method char(2) null comment '差异比较方式',
	limit_percent int null comment '比较阀值(%)',
	diff_deal_method char(1) null comment '差异处理方式',
	last_modify_date char(10) null comment '最后更新日期',
	plugin_conf_id varchar(24) not null comment '配置标识符',
	conf_sort int null comment '配置顺序',
	load_separator varchar(16) null comment '字段分隔符',
	before_load_sql varchar(2000) null comment '装载前执行语句',
	after_load_sql varchar(2000) null comment '装载后执行语句',
	file_charset varchar(16) null comment '文件编码格式',
	load_buffer_size int null comment '缓冲区大小(k)',
	load_faild_deal char(1) null comment '装载失败处理方式',
	load_fields varchar(2000) null comment '指定字段',
	create_table_ddl varchar(2000) null comment '建表语句',
	primary key (table_name,plugin_conf_id)
) engine=innodb default charset=utf8 comment='01.数据装载配置表';
alter table plugin_load_conf comment='01.数据装载配置表';
create table plugin_load_result(
	batch_serial_no varchar(32) not null comment '批次流水号',
	batch_date char(10) null comment '批次日期',
	table_name varchar(38) not null comment '数据库表名',
	table_cnname varchar(80) null comment '表中文名称',
	table_type char(1) null comment '数据表类型',
	up_sysname varchar(80) null comment '供数系统名称',
	table_load_mode char(1) null comment '数据装载方式',
	load_from_file varchar(100) null comment '文件路径',
	file_size decimal(10,2) null comment '文件大小',
	start_time varchar(24) null comment '启动时间',
	end_time varchar(24) null comment '结束时间',
	cost_time decimal(10,2) null comment '运行时长',
	read_rows int null comment '读入条数',
	load_rows int null comment '装入条数',
	reject_rows int null comment '拒绝条数',
	remark varchar(254) null comment '备注',
	load_result char(1) null comment '导数结果',
	batch_id varchar(24) null comment '批次标识',
	primary key (batch_serial_no,table_name)
) engine=innodb default charset=utf8 comment='02.数据装载结果表';
alter table plugin_load_result comment='02.数据装载结果表';
create table plugin_job_conf(
	plugin_conf_id varchar(24) not null comment '配置标识符',
	conf_sort int not null comment '配置顺序',
	job_name varchar(32) null comment '作业名称',
	sql_purpose char(1) null comment 'sql脚本用途',
	sql_content nvarchar(4000) null comment 'sql脚本',
	param_group_id varchar(40) null comment '参数组标识',
	job_faild_deal char(1) null comment '作业失败处理方式',
	last_modify_date char(10) null comment '最后更新日期',
	job_implement varchar(128) null comment '作业实现',
	primary key (plugin_conf_id,conf_sort)
) engine=innodb default charset=utf8 comment='01.数据作业配置';
alter table plugin_job_conf comment='01.数据作业配置';
create table plugin_job_param(
	param_group_id varchar(40) not null comment '参数组标识',
	job_param_name varchar(32) not null comment '作业参数名称',
	job_param_value varchar(512) null comment '作业参数值',
	primary key (param_group_id,job_param_name)
) engine=innodb default charset=utf8 comment='02.数据作业参数配置';
alter table plugin_job_param comment='02.数据作业参数配置';
create table plugin_sc_conf(
	plugin_conf_id varchar(24) null comment '配置标识符',
	conf_sort int null comment '配置顺序',
	sc_desc varchar(80) null comment '转存清数描述',
	func_code int null comment '指令/函数编号',
	func_params longtext null comment '指令/函数参数',
	check_faild_deal char(1) null comment '检查失败处理方式'
) engine=innodb default charset=utf8 comment='01.转存清数配置';
alter table plugin_sc_conf comment='01.转存清数配置';
create table plugin_sc_func(
	func_code int null comment '指令/函数编号',
	func_name varchar(80) null comment '指令/函数名称'
) engine=innodb default charset=utf8 comment='02.转存清数指令/函数';
alter table plugin_sc_func comment='02.转存清数指令/函数';
create table plugin_define(
	plugin_id int not null comment '应用插件id',
	plugin_name nvarchar(64) null comment '应用插件名称',
	plugin_class varchar(254) null comment '应用插件实现类',
	plugin_desc nvarchar(254) null comment '应用插件描述',
	need_other_ds_var char(1) null comment '是否需要其它数据源变量',
	plugin_config_url varchar(64) null comment '应用插件配置地址',
	plugin_catalog char(2) null comment '应用插件分类',
	primary key (plugin_id)
) engine=innodb default charset=utf8 comment='01.应用插件信息';
alter table plugin_define comment='01.应用插件信息';
create table plugin_exec_log(
	batch_sn varchar(32) null comment '批次流水号',
	plugin_id int null comment '应用插件id',
	plugin_name nvarchar(64) null comment '应用插件名称',
	action_name nvarchar(254) null comment '操作名称',
	action_result char(1) null comment '操作结果',
	action_detail_info varchar(4000) null comment '操作详情',
	task_id varchar(24) null comment '任务编号',
	task_name nvarchar(64) null comment '任务名称',
	record_time varchar(24) null comment '记录时间'
) engine=innodb default charset=utf8 comment='02.应用插件执行日志';
alter table plugin_exec_log comment='02.应用插件执行日志';
create table s_deptuser(
	organno nvarchar(16) not null comment '机构码',
	depno nvarchar(16) null comment '部门码',
	actorno nvarchar(8) not null comment '用户码',
	state decimal(22) null comment '状态',
	orgid nvarchar(16) null comment '组织号',
	primary key (organno,actorno)
) engine=innodb default charset=utf8 comment='用户部门关系表';
alter table s_deptuser comment='用户部门关系表';
create table s_role(
	roleno nvarchar(4) not null comment '角色码',
	rolename nvarchar(40) null comment '角色名称',
	orderno decimal(22) null comment '排序字段',
	type decimal(22) null comment '类型',
	memo nvarchar(60) null comment '备注',
	orgid nvarchar(16) null comment '组织号',
	primary key (roleno)
) engine=innodb default charset=utf8 comment='角色控制配置表';
alter table s_role comment='角色控制配置表';
create table s_roleuser(
	roleno char(4) not null comment '角色码',
	actorno nvarchar(8) not null comment '用户码',
	state decimal(22) null comment '状态',
	orgid nvarchar(16) null comment '组织号',
	primary key (roleno,actorno)
) engine=innodb default charset=utf8 comment='用户角色关系表';
alter table s_roleuser comment='用户角色关系表';
create table s_autocode(
	atype nvarchar(20) null comment '类型',
	owner nvarchar(20) null comment '拥有者',
	initcycle char(1) null comment '未知',
	cur_sernum nvarchar(50) null comment '当前序号值',
	zero_flg char(1) null comment '未知',
	cur_date nvarchar(10) null comment '当前日期'
) engine=innodb default charset=utf8 comment='自动序号';
alter table s_autocode comment='自动序号';
create table s_resourceaction(
	resourceid nvarchar(32) not null comment '资源id',
	actid nvarchar(32) not null comment '操作id',
	descr nvarchar(50) null comment '描述',
	flag nvarchar(50) null comment '标志',
	confirmmsg nvarchar(100) null comment '提示确认信息',
	orgid nvarchar(16) null comment '组织号',
	primary key (resourceid,actid)
) engine=innodb default charset=utf8 comment='资源操作';
alter table s_resourceaction comment='资源操作';
create table s_resource(
	resourceid nvarchar(32) not null comment '资源id',
	cnname nvarchar(50) null comment '选项名称',
	systempk nvarchar(20) null comment '所属子系统',
	url nvarchar(254) null comment '资源url',
	parentid nvarchar(50) null comment '上级资源id',
	tablename nvarchar(50) null comment '表名',
	procid nvarchar(100) null comment '流程模板',
	orderid nvarchar(20) null comment '序号',
	memo nvarchar(50) null comment '备注',
	icon nvarchar(100) null comment '资源图标',
	orgid nvarchar(16) null comment '组织号',
	primary key (resourceid)
) engine=innodb default charset=utf8 comment='资源定义';
alter table s_resource comment='资源定义';
create table pub_sys_info(
	sys_id nvarchar(32) not null comment '系统编号',
	sys_name nvarchar(100) null comment '系统名称',
	headoffice nvarchar(20) null comment '未知',
	progress char(2) null comment '未知',
	openday nvarchar(10) null comment '营业日期',
	last_openday nvarchar(10) null comment '上一营业日期',
	effectivedays decimal(22) null comment '未知',
	sys_status nvarchar(2) null comment '系统状态',
	loginmode char(1) null comment '未知',
	slmode char(1) null comment '未知',
	primary key (sys_id)
) engine=innodb default charset=utf8 comment='系统营业日期';
alter table pub_sys_info comment='系统营业日期';
create table s_roleright(
	roleno char(4) null comment '角色编号',
	resourceid nvarchar(32) null comment '资源id',
	actid nvarchar(32) null comment '操作id',
	state decimal(22) null comment '状态',
	orgid nvarchar(16) null comment '组织号'
) engine=innodb default charset=utf8 comment='资源权限';
alter table s_roleright comment='资源权限';

