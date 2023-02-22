package com.dsware.om.client.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 错误码枚举类
 *
 * @author     z00194581
 * @version    [V100R001C00]
 * @see        [相关类/方法]
 * @since      [产品/模块版本]
 */
public enum DSwareErrorCode {
    /**
     * DSware 错误50
     */
    DSWARE_ERROR(50000001, "DSware error"),

    /**
     * 重复请求
     */
    DSW_VBS_DUPLICATE_REQUEST(50150001, "Receive a duplicate request"),

    /**
     * 命令不支持
     */
    DSW_VBS_CMD_NOT_SUPPORTED(50150002, "command type is not support"),

    /**
     * 命令格式错误
     */
    DSW_VBS_CMD_FORMAT_ERROR(50150003, "command format is error"),

    /**
     * 与主VBS进程不通
     */
    DSW_VBS_UNKNOW_MAJOR_VBS(50150004, "lost contact with major vbs"),

    /**
     * 卷不存在
     */
    DSW_VBS_VOLUME_NOT_EXIST(50150005, "volume is not exist"),

    /**
     * 快照不存在
     */
    DSW_VBS_SNAPSHOT_NOT_EXIST(50150006, "snapshot is not exist"),

    /**
     * 卷已经存在
     */
    DSW_VBS_VOLUME_ALREADY_EXIST(
            50150007, "volume already exists or name exists or name duplicates with a snapshot name"),

    /**
     * 快照已经存在
     */
    DSW_VBS_SNAPSHOT_ALREADY_EXIST(50150008, "the snapshot has already existed"),

    /**
     * 存储池空间不足
     */
    DSW_VBS_SPACE_NOT_ENOUGH(50150009, "Storage pool is not enough"),

    /**
     * 节点类型不正确
     */
    DSW_VBS_NODE_TYPE_ERROR(50150010, "the node type is error"),

    /**
     * 卷和快照总数量超过限制
     */
    DSW_VBS_VOLUME_AND_SNAPSHOT_BEYOND_MAX(50150011, "volume and snapshot number is beyond max"),

    /**
     * vbs未就绪
     */
    DSW_VBS_NOT_READY(50150012, "vbs is not ready"),

    /**
     * 节点引用次数大于0
     */
    DSW_VBS_NODE_REFERENCE_LARGE_ZERO(50150013, "the ref num of node is not 0"),

    /**
     * The volume is not in the pre-deletion state.
     */
    DSW_VBS_PRE_DEL_VOLUME_STATUS_ERROR(50150014, "The volume is not in the pre-deletion state."),

    /**
     * The storage resource pool is faulty
     */
    DSW_OSD_NOT_READY(50150015, "The storage resource pool is faulty"),

    /**
     * VBS 处理队列忙
     */
    DSW_VBS_QUEUE_BUSY(50150016, "VBS handle queue busy"),

    /**
     * VBS Proxy 处理超时
     */
    DSW_VBS_HANDLE_REQ_TIMEOUT(50150017, "VBS handle req timeout"),

    /**
     * 元数据被锁
     */
    DSW_VBS_METABLOCK_IS_LOCKED(50150020, "VBS metablock is locked"),

    /**
     * VBS pool is not exist
     */
    DSW_VBS_POOL_NOT_EXIST(50150021, "VBS pool is not exist"),

    /**
     * VBS is not ok
     */
    DSW_VBS_NOT_OK(50150022, "VBS is not ok"),

    /**
     * VBS pool is not ok
     */
    DSW_VBS_POOL_NOT_OK(50150023, "VBS pool is not ok"),

    /**
     * VBS is not exist
     */
    DSW_VBS_NOT_EXIST(50150024, "VBS is not exist"),

    /**
     * VBS load scsi-3 lock pr meta failed
     */
    DSW_VBS_PR_META_ERROR(50150064, "VBS load scsi-3 lock pr meta failed"),

    /**
     * The disaster recovery relationship exists
     */
    DSW_VBS_DR_RELATION_EXIST_ERROR(50150100, "The disaster recovery relationship exists"),

    /**
     * The DR relationship does not exist
     */
    DSW_VBS_DR_RELATION_NO_EXIST_ERROR(50150101, "The DR relationship does not exist"),

    /**
     * 卷存在镜像
     */
    DSW_VBS_VOL_EXIST_MIRROR(50150102, "volume has exist mirror"),

    /**
     * The volume does not have a mirror
     */
    DSW_VBS_VOL_NOT_MIRROR(50150103, "The volume does not have a mirror"),

    /**
     * Incorrect volume status
     */
    DSW_VBS_VOL_STATUS_ERROR(50150104, "Incorrect volume status"),

    /**
     * The mirror volume already exists
     */
    DSW_VBS_EXIST_MIRROR_VOL(50150105, "The mirror volume already exists"),

    /**
     * 卷镜像关系错误
     */
    DSW_VBS_MIRROR_ROLE_ERROR(50150106, "volume mirror role error"),

    /**
     * The volume mirroring relationship is invalid
     */
    DSW_VBS_MIRROR_RELATION_ERROR(50150107, "The volume mirroring relationship is invalid"),

    /**
     * This operation is not allowed because the Link Status is invalid
     */
    DSW_VBS_MIRROR_RUN_STATUS_ERROR(50150108, "This operation is not allowed because the Link Status is invalid"),

    /**
     * The volume mirror has been activated
     */
    DSW_VBS_VOL_MIRROR_ACTIVATED(50150109, "The volume mirror has been activated"),

    /**
     * The volume is being activated
     */
    DSW_VBS_VOL_MIRROR_ACTIVATING(50150110, "The volume is being activated"),

    /**
     * The mirror volume does not match
     */
    DSW_VBS_VOL_MIRROR_DISMATCH(50150111, "The mirror volume does not match"),

    /**
     * The volume mirror is being deactivated
     */
    DSW_VBS_VOL_MIRROR_DEACTIVATEING(50150112, "The volume mirror is being deactivated"),

    /**
     * The mirror volume is not activated
     */
    DSW_VBS_VOL_MIRROR_INACTIVE(50150113, "The mirror volume is not activated"),

    /**
     * Incorrect volume mirror capacity
     */
    DSW_VBS_VOL_MIRROR_SIZE_ERROR(50150114, "Incorrect volume mirror capacity"),

    /**
     * Incorrect DR mirror status
     */
    DSW_VBS_DR_STATUS_ERROR(50150115, "Incorrect DR mirror status"),

    /**
     * can not delete HyperMetro volume
     */
    DSW_DELETE_HYPERMETRO_VOLUME_ERROR(50150055, "Volume is HyperMetro volume,can not delete,replicate and migrate."),

    /**
     * The number of volume mirrors exceeds the upper limit
     */
    DSW_VBS_CREATE_MIRROR_VOL_BEYOND_MAX(50150116, "The number of volume mirrors exceeds the upper limit"),

    /**
     * No disaster recovery volumes are available in the memory
     */
    DSW_VBS_DR_VOL_NOT_EXIST_IN_MEM(50150117, "No disaster recovery volumes are available in the memory"),

    /**
     * Failed to clear the mirror attachment information.Please try again later
     */
    DSW_VBS_CLEAN_ATTACH_MIRROR_INFO_ERROR(50150118, "Failed to clear the mirror attachment information."),

    /**
     * The DR feature is disabled
     */
    DSW_VBS_DR_NOT_RUN_ERROR(50150119, "The DR feature is disabled"),

    /**
     * The DR volume does not exist
     */
    DSW_VBS_DR_VOL_EXIST_ERROR(50150120, "The DR volume does not exist"),

    /**
     * The disaster recovery relationship parameters are inconsistent with the system configuration
     */
    DSW_VBS_MIRROR_IP_ERROR(50150121, "The disaster recovery relationship parameters error"),

    /**
     * The mirroring link is faulty
     */
    DSW_VBS_MIRROR_COMMUNICATION_LINK_ERROR(50150122, "The mirroring link is faulty"),

    /**
     * Invalid replication IP address
     */
    DSW_VBS_INVALID_MIRROR_DUP_IP(50150123, "Invalid replication IP address"),

    /**
     * The VBS module has not detected the repeated messages of the mirror volume
     */
    DSW_VBS_NOT_FOUND_MIRROR_DUP_NET_INFO(50150124, "VBS has not detected the repeated messages of the mirror volume"),

    /**
     * 镜像卷处于同步状态
     */
    DSW_VBS_ATTACH_MIRROR_VOL_SYNC_PROGRESS(50150125, "mirror vol is in sync progress"),

    /**
     * 卷的镜像卷正在同步
     */
    DSW_VBS_CREATE_SNAP_MIRROR_VOL_SYNC_PROGRESS(50150126, "volume mirror is sync progressing"),

    /**
     * The activated volume has multiple mount points
     */
    DSW_VBS_ACTIVE_MIRROR_VOL_MULTI_ATTACH(50150127, "The activated volume has multiple mount points"),

    /**
     * Invalid DR policy setting
     */
    DSW_VBS_DR_PARA_STRATEGY_ERROR(50150128, "Invalid DR policy setting"),

    /**
     * Incorrect wait time for creating a DR volume
     */
    DSW_VBS_DR_PARA_FAULT_WAIT_TIME_ERROR(50150129, "Incorrect wait time for creating a DR volume"),

    /**
     * Invalid DUP format
     */
    DSW_VBS_DR_PARA_DUP_INFO_FORMAT_ERROR(50150130, "Invalid DUP format"),

    /**
     * No cluster information is available
     */
    DSW_VBS_DR_PARA_CLUSTER_NOT_EXIST(50150131, "No cluster information is available"),

    /**
     * The VBS process does not exist
     */
    DSW_VBS_DR_PARA_VBS_NOT_EXIST(50150132, "The VBS process does not exist"),

    /**
     * The DUP IP address does not exist
     */
    DSW_VBS_DR_PARA_DUP_IP_NOT_EXIST(50150133, "The DUP IP address does not exist"),

    /**
     * Invalid DUP IP address
     */
    DSW_VBS_DR_PARA_DUP_IP_FORMAT_ERROR(50150134, "Invalid DUP IP address"),

    /**
     * The DUP port does not exist
     */
    DSW_VBS_DR_PARA_DUP_PORT_NOT_EXIST(50150135, "The DUP port does not exist"),

    /**
     * The number of local mirrors does not equal to the number of remote mirrors
     */
    DSW_VBS_DR_PARA_DUP_INFO_NUM_NO_EQUAL(50150136, "The number of local mirrors error"),

    /**
     * The port number exceeds the threshold
     */
    DSW_VBS_DR_PARA_PORT_RANGE_ERROR(50150137, "The port number exceeds the threshold"),

    /**
     * The cluster ID exceeds the threshold
     */
    DSW_VBS_DR_PARA_CID_RANGE_ERROR(50150138, "The cluster ID exceeds the threshold"),

    /**
     * The VBS ID exceeds the threshold
     */
    DSW_VBS_DR_PARA_VBS_ID_RANGE_ERROR(50150139, "The VBS ID exceeds the threshold"),

    /**
     * 卷名不合法
     */
    DSW_VBS_VOLUME_NAME_INVALID(50151001, "volume name is invalid"),

    /**
     * 卷正处于删除状态
     */
    DSW_VBS_VOLUME_IS_DELETING(50151002, "volume is being deleting"),

    /**
     * 卷大小不合法
     */
    DSW_VBS_VOLUME_SIZE_INVALID(50151003, "the volume size invalid"),

    /**
     * 创建卷状态不正常
     */
    DSW_VBS_VOLUME_STATUS_ABNORMAL(50151004, "vbs create volume status is abnormal"),

    /**
     * 当前卷正在复制中
     */
    DSW_VBS_VOLUME_IS_DUPLICATING(50151005, "volume is duplicating"),

    /**
     * Bitmap卷正在创建
     */
    DSW_VBS_BITMAP_VOLUME_IS_CREATING(50151006, "bitmap volume is creating"),

    /**
     * 卷或快照数据扇区损坏
     */
    DSW_VBS_NODE_DATA_IN_BAD_SECTOR(50151007, "volume/snapshot data is in bad disk sector"),

    /**
     * 备VBS节点未完成挂载信息同步
     */
    DSW_VBS_SLAVE_NODE_NOT_READY(50151008, "slave vbs is not ready, attach info not synced"),

    /**
     * 备VBS节点down
     */
    DSW_VBS_IS_DOWN(50150067, "target vbs is down"),

    /**
     * 池ID范围越界
     */
    POOL_ID_IS_OUT_OF_RANGE(50151009, "pool id is out of range"),

    /**
     * 池ID没有使用
     */
    POOL_ID_IS_NOT_USE(50151010, "pool id is not use"),

    /**
     * 存储池没有准备好
     */
    STORAGE_POOL_NOT_READY(50151011, "Storage pool is not ready"),

    /**
     * 部分存储池没有准备好
     */
    STORAGE_POOL_SOME_NOT_READY(50151012, "Storage pool is not ready"),

    /**
     * 存储池类型非法
     */
    DSW_VBS_POOL_TYPE_INVALID(50151013, "pool type is invalid"),

    /**
     * The pool is in emergency mode
     */
    DSW_VBS_POOL_IN_EMERGENCY_MODE(50151015, "The pool is in emergency mode"),

    /**
     * The entered label value already exists
     */
    DSW_VBS_INPUT_LABLE_EXIST(50151016, "The entered label value already exists"),

    /**
     * Failed to find an available storage pool
     */
    DSW_VBS_NO_AVAILABLE_POOL(50151026, "Failed to find an available storage pool"),

    /**
     * 快照名不合法
     */
    DSW_VBS_SNAPSHOT_NAME_INVALID(50151201, "snapshot name is invalid"),

    /**
     * 快照正处于删除状态
     */
    DSW_VBS_SNAPSHOT_IS_DELETING(50151202, "snapshot being deleted"),

    /**
     * 快照正处于复制状态
     */
    DSW_VBS_SNAPSHOT_IS_DUPLICATING(50151203, "snapshot being duplicated"),

    /**
     * 卷被挂载到多个节点，无法创建快照
     */
    DSW_VBS_VOLUME_ATTACH_TOO_MANY(50151204, "volume attach in many target for create snapshot"),

    /**
     * 创建快照挂载点响应超时
     */
    DSW_VBS_CREATE_SNAP_TARGET_TIMEOUT(50151205, "target response timeout when creating snap"),

    /**
     * 后台正在执行创建快照操作
     */
    DSW_VBS_CREATE_SNAP_BACKGROUND_IS_CREATING(50151206, "snap is still creating under background"),

    /**
     * 创建快照时，节点类型非法
     */
    DSW_VBS_CREATE_SNAP_NODE_TYPE_INVALID(50151207, "volume node type error when creating snap"),

    /**
     * 创建快照达到最大值
     */
    DSW_VBS_CREATE_SNAP_MAX_ID_ERROR(50151208, "create snap exceed max"),

    /**
     * Communication between active and standby sites timed out
     */
    DSW_VBS_OPERATE_TIMEOUT(50151209, "Communication between active and standby sites timed out"),

    /**
     * iSCSI卷不支持创建快照
     */
    DSW_VBS_CREATE_SNAP_IS_ISCSI_LUN(50151210, "iscsi volume can not create snap"),

    /**
     * Volume 正在迁移
     */
    VOLUME_IS_MIGRATING(50151211, "volume is migrating"),

    /**
     * 有快照的卷不能迁移
     */
    VOLUME_HAS_SNAPSHOT(50151212, "volume has snapshot cannot migrate"),

    /**
     * The volume already exists in the destination pool
     */
    DSW_VBS_VOLUME_HAVE_EXISTED(50151213, "The volume already exists in the destination pool"),

    /**
     * iSCSI卷 不能迁移
     */
    ISCSI_VOLUME_CANNOT_MIGRATE(50151214, "iscsi volume cannot migrate"),

    /**
     * mirror 卷不能迁移
     */
    MIRROR_VOLUME_CANNOT_MIGRATE(50151215, "mirror volume cannot migrate"),

    /**
     * 原卷和目的卷类型不一致
     */
    VOLUME_TYPE_IS_NOT_MATCH(50151216, "src and dst volume type is not match"),
    /**
     * 卷存在锁，不支持冷迁移
     */
    VOLUME_TYPE_HAS_SCSI_LOCK(50151224, "volume has scsi lock, cannot migrate"),

    /**
     * 卷已经被挂载
     */
    DSW_VBS_VOLUME_HAS_ATTACHED(50151401, "volume has been attached"),

    /**
     * 本vbs挂载卷的数量超过限制512,或者一个卷挂载超过128个挂载点
     */
    DSW_VBS_ATTACHED_BEYOND_MAX(50151402, "attached volume number is beyond max"),

    /**
     * 卷或快照名字不合法
     */
    DSW_VBS_NODE_NAME_INVALID(50151403, "name invalid"),

    /**
     * 卷或快照不存在
     */
    DSW_VBS_NODE_NOT_EXIST(50151404, "volume or snap does not exist"),

    /**
     * The mirroring volume is in the data synchronization state
     */
    DSW_VBS_MIRROR_VOLUME_DATA_SYNCING(50151405, "The mirroring volume is in the data synchronization state"),

    /**
     * 挂载卷为iSCSI卷
     */
    DSW_VBS_NODE_IS_ISCSI(50151406, "iSCSI volume can not attach"),

    /**
     * The volume has no attach point
     */
    DSW_VBS_ATTACHED_NO_VBS(50151407, "The volume has no attach point"),

    /**
     * 被挂载的设备不是块存储设备
     */
    DSW_VBS_NODE_IS_NOT_BLOCK_DEVICE(50151408, "attach volume is not a block storage device"),

    /**
     * 挂载共享卷超过规格，C30为20000个
     */
    DSW_VBS_SHARE_VOL_REACH_MAX(50151409, "share attach volume reach max"),

    /**
     * 挂卷的节点关联的存储池达到了最大值128个
     */
    DSW_VBS_ASSOCIATED_POOL_REACH_MAX_NUM(50151419, "Target vbs associated pool reach max number"),

    /**
     * 卷还没有被挂载
     */
    DSW_VBS_VOLUME_NOT_ATTACH(50151601, "volume has not been attached"),

    /* delete volume&snapshot */
    /**
     * 删除的节点不是卷
     */
    DSW_VBS_DELETE_IS_NOT_VOLUME(50151801, "the node is not volume"),

    /**
     * 删除的节点不是快照
     */
    DSW_VBS_DELETE_IS_NOT_SNAPSHOT(50152001, "the node is not snapshot"),

    /* duplicate snap */
    /**
     * 复制快照源卷不存在
     */
    DSW_VBS_DUPLICATE_SRC_SNAPSHOT_NOT_EXIST(50152201, "source snapshot not exist"),

    /**
     * 复制快照目的卷已经存在
     */
    DSW_VBS_DUPLICATE_DST_SNAPSHOT_ALREADY_EXIST(50152202, "destination snapshot already exist"),

    /**
     * 子快照个数超出范围
     */
    DSW_VBS_SNAP_CHILD_TOO_MUCH(50152401, "create vol from snap, but snap child is much than 255"),

    /**
     *  一颗树的branch 资源不足
     */
    DSW_VBS_BRANCH_TOO_MUCH(50152402, "Branch is too much in a tree,can not create more branch"),

    /**
     *  一颗树的smart cache 资源不足
     */
    DSW_VBS_SMART_CACHE_TOO_MUCH(50152403, "create snap,but smart cache resource is not enough"),

    /**
     * 复制卷和快照个数超过系统允许最大数目
     */
    DSW_VBS_DUPLICATE_SNAP_VOL_EXCEED_LIMIT(50152501, "number of snap and volume duplicating exceeds system limit"),

    /**
     *  查询节点不是卷
     */
    DSW_VBS_NOT_VOLUME_NODE(50152601, "the node is not volume"),

    /**
     * 查询的节点不是快照
     */
    DSW_VBS_NOT_SNAP_NODE(50152801, "the node is not snapshot"),

    /**
     * The queried volume device does not exist
     */
    DSW_VBS_QUERY_DEV_INFO_NOT_EXIST(50152802, "The queried volume device does not exist"),

    /**
     * 当前正在创建的位图卷已达系统上限
     */
    DSW_VBS_CREATE_BITMAP_VOL_EXCEED_LIMIT(50153001, "the bitmap volumes in creating exceeds system limit"),

    /**
     * 差量卷的起始快照或终止快照状态错误
     */
    DSW_VBS_CREATE_BITMAP_VOL_SNAP_STATUS_ERROR(50153002, "the from_snap or to_snap of bitmap volume is in bad status"),

    /**
     * 差量卷的起始快照和终止快照关系错误
     */
    DSW_VBS_CREATE_BITMAP_VOL_SNAP_RELATION_ERROR(
            50153003, "the from_snap and to_snap of bitmap volume is in bad relation"),

    /**
     * 位图卷个数超过系统允许的上限
     */
    DSW_VBS_CUR_BITMAP_VOLUME_NUM_EXCEED_SYSTEM_LIMIT(50153004, "the number of bitmap volume exceeds system limit"),

    /**
     * 位图卷的起始快照和终止快照的branch_id不相同
     */
    DSW_VBS_CREATE_BITMAP_VOL_SNAP_BRANCH_ERR(
            50153007, "The branch_id of the source snapshot is not consistent with the destination snapshot"),

    /**
     * VBS内部参数为NULL
     */
    DSW_VBS_NULL_ARGUMENT(50155001, "VBS internal argument is null"),

    /**
     * VBS内部重复消息
     */
    DSW_VBS_DUPLICATE_MSG(50155002, "VBS internal duplicate message"),

    /**
     * VBS内部元数据错误
     */
    DSW_VBS_METADATA_ERROR(50155003, "VBS internal metadata error"),

    /**
     * VBS内部元数据局部错误
     */
    DSW_VBS_METADATA_PART_ERROR(50155004, "VBS internal metadata partial error"),

    /**
     * VBS内部元数据未知错误
     */
    DSW_VBS_METADATA_UNKNOWN_ERROR(50155005, "VBS internal unknow error"),

    /**
     * The VBS failed to uninstall a disk using the VSC
     */
    DSW_ERROR_SCSI_DETACH_MOUNTED_VOLUME(50155006, "The VBS failed to uninstall a disk using the VSC"),

    /**
     * VBS内部未知错误码
     */
    DSW_VBS_INTERNAL_UNKOWN_ERROR(50155007, "An unknown error occurred on the VBS"),

    /**
     * VBS 同步日志失败
     */
    DSW_VBS_SYNC_JOURNAL_FAILED(50155008, "vbs sync journal fail"),

    /**
     * VBS同步元数据失败
     */
    DSW_VBS_SYNC_METADATA_ERROR(50155009, "VBS sync metadata fail"),

    /**
     * The VBS thin provisioning rate is invalid
     */
    DSW_VBS_INVALID_THIN_RATE(50155010, "The VBS thin provisioning rate is invalid"),

    /**
     * Failed to add iSCSI target portal
     */
    DSW_ISCSI_ADD_PORTAL_FAILED(50155011, "Failed to add iSCSI target portal"),

    /**
     * The portal already exists
     */
    DSW_ISCSI_ADD_PORTAL_ALREADY_EXIST(50155012, "The portal already exists"),

    /**
     * Failed to add a CHAP user
     */
    DSW_ISCSI_ADD_USER_FAILED(50155013, "Failed to add a CHAP user"),

    /**
     * The CHAP user already exists
     */
    DSW_ISCSI_USR_ALREADY_EXIST(50155014, "The CHAP user already exists"),

    /**
     * The CHAP user does not exist
     */
    DSW_ISCSI_USR_NOT_EXIST(50155015, "The CHAP user does not exist"),

    /**
     * Invalid length of the CHAP user password
     */
    DSW_ISCSI_PASSWD_LENGTH_INVALID(50155016, "Invalid length of the CHAP user password"),

    /**
     * Invalid CHAP username
     */
    DSW_ISCSI_USER_NAME_INVALID(50155017, "Invalid CHAP username"),

    /**
     * The iSCSI function is disabled
     */
    DSW_ISCSI_MODULE_DISABLE(50155018, "The iSCSI function is disabled"),

    /**
     * The CHAP function is disabled
     */
    DSW_ISCSI_CHAP_DISABLE(50155019, "The CHAP function is disabled"),

    /**
     * Weak password of the CHAP user
     */
    DSW_ISCSI_CHAP_PASSWORD_WEAK(50155020, "Weak password of the CHAP user"),

    /**
     * Invalid CHAP user password
     */
    DSW_ISCSI_CHAP_PASSWD_INVALID(50155021, "Invalid CHAP user password"),

    /**
     * The number of CHAP users exceeds the upper limit
     */
    DSW_ISCSI_CHAP_USER_MAP_FULL(50155022, "The number of CHAP users exceeds the upper limit"),

    /**
     * Invalid CHAP user type
     */
    DSW_ISCSI_CHAP_DIR_INVALID(50155023, "Invalid CHAP user type"),

    /**
     * The iSCSI port number does not range from 3260 to 3269
     */
    DSW_ISCSI_PORT_RANGE_ERROR(50155024, "The iSCSI port number does not range from 3260 to 3269"),

    /**
     * Failed to connect to the iSCSI service
     */
    DSW_ISCSI_TCP_SERVICE_ERROR(50155025, "Failed to connect to the iSCSI service"),

    /**
     * The added iSCSI user failed to apply for memory resources
     */
    DSW_ISCSI_ADD_USER_ALLOC_MEM_FAILED(50155026, "The added iSCSI user failed to apply for memory resources"),

    /**
     * The CHAP user cannot be deleted because it has a port bound
     */
    DSW_ISCSI_USER_INUSE(50155027, "The CHAP user cannot be deleted because it has a port bound"),

    /**
     * Invalid initiator name
     */
    DSW_ISCSI_PORT_NAME_INVALID(50155100, "Invalid initiator name"),

    /**
     * The number of ports has reached the upper limit
     */
    DSW_ISCSI_PORT_NUM_OVER(50155101, "The number of ports has reached the upper limit"),

    /**
     * The initiator already exists
     */
    DSW_ISCSI_PORT_ALREADY_EXIST(50155102, "The initiator already exists"),

    /**
     * The initiator does not exist or is not authenticated
     */
    DSW_ISCSI_PORT_NOT_EXIST_OR_AUTHED(50155103, "The initiator does not exist or is not authenticated"),

    /**
     * Failed to delete the initiator because it is already mapped to the CHAP user
     */
    DSW_ISCSI_PORT_ALREADY_BIND_WTH_CHAP(50155104, "Failed to delete the initiator because it mapped a CHAP user"),

    /**
     * The port has been bound to a CHAP user
     */
    DSW_ISCSI_PORT_NOT_BIND_WTH_CHAP(50155105, "The port has been bound to a CHAP user"),

    /**
     * Failed to delete the initiator because it is already mapped to the host
     */
    DSW_ISCSI_PORT_ALREADY_BIND_WTH_HOST(50155106, "Failed to delete the initiator because it mapped to host"),

    /**
     * The port is not associated with the host
     */
    DSW_ISCSI_PORT_NOT_BIND_WTH_HOST(50155107, "The port is not associated with the host"),

    /**
     * The number of port CHAP users has reached the upper limit
     */
    DSW_ISCSI_PORT_CHAP_USER_NUM_OVER(50155109, "The number of port CHAP users has reached the upper limit"),

    /**
     * The port does not support outgoing CHAP users"
     */
    DSW_ISCSI_PORT_NOT_SUPPORT_BIND_OUTGOING_USER(50155110, "The port does not support outgoing CHAP users"),

    /**
     * Incorrect CHAP status parameter
     */
    DSW_ISCSI_PORT_CHAP_STATUS_INVALID(50155111, "Incorrect CHAP status parameter"),

    /**
     * The LUN ID already exists
     */
    ISCSI_VOL_RETCODE_EXIST_LUN_ID(50156010, "The LUN ID already exists"),

    /**
     * The LUN ID does not exist
     */
    ISCSI_VOL_RETCODE_LUN_ID_NOT_EXIST(50156011, "The LUN ID does not exist"),

    /**
     * No LUN ID is available
     */
    ISCSI_VOL_RETCODE_LUN_ID_EXHAUST(50156012, "No LUN ID is available"),

    /**
     * The VBS port is beyond the specified port range
     */
    DSW_VBS_PORT_RANGE_ERROR(50156013, "The VBS port is beyond the specified port range"),

    /**
     * Incorrect URL configuration
     */
    DSW_VBS_URL_CONFIG_ERROR(50156014, "Incorrect URL configuration"),

    /**
     * Incorrect VBS IP address
     */
    DSW_VBS_URL_IP_ERROR(50156015, "Incorrect VBS IP address"),

    /**
     * The number of CHAP users has reached the upper limit
     */
    DSW_VBS_ISCSI_CHAP_USER_NUM_ERROR(50156016, "The number of CHAP users has reached the upper limit"),

    /**
     * Invalid number of iSCSI clients
     */
    DSW_VBS_ISCSI_NUM_ERROR(50156017, "Invalid number of iSCSI clients"),

    /**
     * Incorrect iSCSI ID type
     */
    DSW_VBS_ISCSI_ID_TYPE_ERROR(50156018, "Incorrect iSCSI ID type"),

    /**
     * Incorrect remote contorl IP address of the VBS node
     */
    DSW_VBS_URL_MIRROR_IP_ERROR(50156019, "Incorrect remote contorl IP address of the VBS node"),

    /**
     * The VBS pre-deletion time exceeds the upper limit
     */
    DSW_VBS_PRE_DEL_TIME_ERROR(50156020, "The VBS pre-deletion time exceeds the upper limit"),

    /**
     * The client ID exceeds the upper limit
     */
    DSW_VBS_CLIENT_ID_EXCEED(50156021, "The client ID exceeds the upper limit"),

    /**
     * A fault occurs when the client obtains the MDC ID and port number
     */
    DSW_VBS_CLIENT_GET_MDC_URL_FAILED(50156022, "A fault occurs when the client obtains the MDC ID and port number"),

    /**
     * A fault occurs when the client checks the I/O depth
     */
    DSW_VBS_CLIENT_CHECK_IO_DEPTH_ERROR(50156023, "A fault occurs when the client checks the I/O depth"),

    /**
     * The client I/O timeout configuration is incorrect
     */
    DSW_VBS_CLIENT_IO_RETRY_ERROR(50156024, "The client I/O timeout configuration is incorrect"),

    /**
     * The timeout duration for the client to obtain MDC information is incorrect
     */
    DSW_VBS_CLIENT_CTL_RETRY_ERROR(50156025, "The timeout duration for client to obtain MDC information is incorrect"),

    /**
     * The VBS allocate memory fails
     */
    DSW_VBS_CLIENT_MEM_MALLOC_ERR(50156026, "The VBS allocate memory fails"),

    /**
     * The iSCSI module is disabled
     */
    DSW_ISCSI_LUN_MAPPING_DISABLE(50157000, "The iSCSI module is disabled"),

    /**
     * The mapping between the volume and the specified host already exists
     */
    DSW_VOLUME_ALREADY_EXIST_IN_MAPPING(50157001, "The volume already mapped to the host"),

    /**
     * Invalid volume type
     */
    DSW_SCSI_VOLUME_CAN_NOT_BE_MAPPED(50157002, "Invalid volume type"),

    /**
     * Invalid volume name
     */
    DSW_VOLUME_PARAMETER_INVALID(50157003, "Invalid volume name"),

    /**
     * Lun id is already in use
     */
    DSW_LUN_ALREADY_EXIST_IN_MAP(50157004, "Lun id is already in use"),

    /**
     * Only support one lun id to be processed
     */
    DSW_LUN_NO_AVAILABLE(50157005, "Only support one lun id to be processed"),

    /**
     * Lun id is invalid
     */
    DSW_LUN_PARAMETER_INVALID(50157006, "Lun id is invalid"),

    /**
     * The number of volumes to be mapped or unmapped exceeds limit
     */
    DSW_LUN_ID_OUT_OF_LIMIT(50157007, "The number of volumes to be mapped or unmapped exceeds limit"),

    /**
     * Failed to associate the iSCSI volume to the host
     */
    DSW_ISCSI_LUN_MAPPING_FAILED(50157008, "Failed to associate the iSCSI volume to the host"),

    /**
     * Failed to allocate memory when doing broadcast
     */
    DSW_ISCSI_LUN_MAPPING_ALLOC_MEM_FAILED(50157009, "Failed to allocate memory when doing broadcast"),

    /**
     * 卷存在映射关系
     */
    DSW_VBS_VOLUME_HAS_MAPPING(50157010, "volume has mapping"),

    /**
     * The volume name already exists
     */
    DSW_VOLUME_NAME_DUPLICATE(50157011, "The volume name already exists"),

    /**
     * The volume does not belong to the host
     */
    DSW_VOLUME_NOT_EXIST_IN_MAPPING(50157012, "The volume does not belong to the host"),

    /**
     * Invalid volume name length
     */
    DSW_VOLUME_NAME_LENGTH_INVALID(50157013, "Invalid volume name length"),

    /**
     * Invalid host name
     */
    DSW_ISCSI_HOST_NAME_INVALID(50157015, "Invalid host name"),

    /**
     * Invalid host name length
     */
    DSW_ISCSI_HOST_NAME_LENGTH_INVALID(50157016, "Invalid host name length"),

    /**
     * The number of hosts to be created exceeds the upper limit
     */
    DSW_ISCSI_HOST_NUM_EXCEED(50157017, "The number of hosts to be created exceeds the upper limit"),

    /**
     * Incorrect number of iSCSI hosts
     */
    DSW_ISCSI_HOST_NUM_IS_ERROR(50157018, "Incorrect number of iSCSI hosts"),

    /**
     * The host already exists
     */
    DSW_ISCSI_HOST_ALREADY_EXISTED(50157019, "The host already exists"),

    /**
     * The initiator is not mapped to the host
     */
    DSW_ISCSI_HOST_PORT_RELATION_NOT_EXISTED(50157020, "The initiator is not mapped to the host"),

    /**
     * Failed to delete the host
     */
    DSW_ISCSI_HOST_PORT_RELATION_ALREADY_EXISTED(50157021, "Failed to delete the host"),

    /**
     * The host does not exist
     */
    DSW_ISCSI_HOST_NOT_EXISTED(50157022, "The host does not exist"),

    /**
     * The number of initiators mapped to the host exceeds the upper limit
     */
    DSW_ISCSI_HOST_PORT_NUM_EXCEED(50157023, "The number of initiators mapped to the host exceeds the upper limit"),

    /**
     * The host failed to apply for memory (internal error)
     */
    DSW_ISCSI_HOST_ALLOC_MEM_FAILED(50157024, "The host failed to apply for memory (internal error)"),

    /**
     * Failed to assign the host an ID
     */
    DSW_ISCSI_ALLOC_HOST_ID_ERROR(50157025, "Failed to assign the host an ID"),

    /**
     * Some initiators are not mapped to hosts
     */
    DSW_ISCSI_HOST_PORT_NUM_SHORTAGE(50157026, "Some initiators are not mapped to hosts"),

    /**
     * The host has volumes attached
     */
    DSW_ISCSI_HOST_LUN_RELATION_ALREADY_EXISTED(50157027, "The host has volumes attached"),

    /**
     * Failed to process host
     */
    DSW_ISCSI_HOST_ADD_MAP_FAILED(50157028, "Failed to process host"),

    /**
     * The initiator name already exists
     */
    DSW_ISCSI_HOST_ADD_PORT_NAME_DUPLICATE(50157029, "The initiator name already exists"),

    /**
     * The number of opreating ports is already out of limit
     */
    DSW_ISCSI_HOST_BATCH_HOST_PORT_NUM_TOO_MUCH(50157030, "The number of opreating ports is already out of limit"),

    /**
     * Incorrect host group name format
     */
    DSW_ISCSI_HOSTGROUP_NAME_INVALID(50157040, "Incorrect host group name format"),

    /**
     * Invalid host group name length
     */
    DSW_ISCSI_HOSTGROUP_NAME_LENGTH_INVALID(50157041, "Invalid host group name length"),

    /**
     * The number of host groups has reached the upper limi
     */
    DSW_ISCSI_HOSTGROUP_NUM_EXCEED(50157042, "The number of host groups has reached the upper limit"),

    /**
     * hostgroup number is 0
     */
    DSW_ISCSI_HOSTGROUP_NUM_IS_ZERO(50157043, "hostgroup number is 0"),

    /**
     * The host cluster already exists
     */
    DSW_ISCSI_HOSTGROUP_ALREADY_EXISTED(50157044, "The host cluster already exists"),

    /**
     * The host cluster does not have mapped hosts
     */
    DSW_ISCSI_HOSTGROUP_HOST_RELATION_NOT_EXISTED(50157045, "The host cluster does not have mapped hosts"),

    /**
     * Failed to delete the host cluster
     */
    DSW_ISCSI_HOSTGROUP_HOST_RELATION_ALREADY_EXISTED(50157046, "Failed to delete the host cluster"),

    /**
     * The host cluster does not exist
     */
    DSW_ISCSI_HOSTGROUP_NOT_EXISTED(50157047, "The host cluster does not exist"),

    /**
     * The number of hosts to be added to the host cluster exceeds the upper limit
     */
    DSW_ISCSI_HOSTGROUP_HOST_NUM_EXCEED(50157048, "The number of hosts to be added to host cluster over limit"),

    /**
     * The host group failed to apply for memory (internal error)
     */
    DSW_ISCSI_HOSTGROUP_ALLOC_MEM_FAILED(50157049, "The host group failed to apply for memory (internal error)"),

    /**
     * Failed to assign the host group an ID
     */
    DSW_ISCSI_ALLOC_HOSTGROUP_ID_ERROR(50157050, "Failed to assign the host group an ID"),

    /**
     * The number of hosts to be deleted has exceeds the number of hosts associated with the host group
     */
    DSW_ISCSI_HOSTGROUP_HOST_NUM_SHORTAGE(50157051, "The number of hosts to be deleted over limit"),

    /**
     * The host cluster has volumes attached
     */
    DSW_ISCSI_HOSTGROUP_LUN_RELATION_ALREADY_EXISTED(50157052, "The host cluster has volumes attached"),

    /**
     * Add host group failed
     */
    DSW_ISCSI_HOSTGROUP_ADD_MAP_FAILED(50157053, "Add host group failed"),

    /**
     * The number of host groups has reached the upper limit
     */
    DSW_VBS_ISCSI_HOSTGROUP_NUM_ERROR(50157054, "The number of host groups has reached the upper limit"),

    /**
     * No host mapping to the specified host group is found
     */
    DSW_VBS_ISCSI_HOST_NOT_EXIST_IN_HOSTGRUP(50157055, "No host mapping to the specified host group is found"),

    /**
     * No host group mapping to the specified iSCSI volume is found
     */
    DSW_VBS_ISCSI_VOLUME_NOT_EXIST_IN_HOSTGRUP(50157056, "No host group mapping to the specified iSCSI volume found"),

    /**
     * The host name already exists
     */
    DSW_ISCSI_HOSTGROUP_ADD_HOST_NAME_DUPLICATE(50157057, "The host name already exists"),

    /**
     * The host cannot be added to the host group because it maps an iSCSI volume
     */
    DSW_ISCSI_HOST_LUN_RELATION_ALREADY_EXISTED_IN_HOSTGROUP(50157058, "Host maps an volume cannot add to host group"),

    /**
     * Host has mapped to other hostgroup
     */
    DSW_ISCSI_HOST_ALREADY_MAPPED_TO_OTHER_HOSTGROUP(50157059, "Host has mapped to other hostgroup"),

    /**
     * Some parameters do not exist
     */
    DSW_ISCSI_QUERY_CONFIG_PARAM_INVALID(50157060, "Some parameters do not exist"),

    /**
     * Duplicate parameters in the request
     */
    DSW_ISCSI_QUERY_CONFIG_PARAM_REPEAT(50157061, "Duplicate parameters in the request"),
    /**
     * QOS invalid name
     */
    DSW_QOS_NAME_INVALID(50157200, "Incorrect QoS policy format"),
    /**
     * the length of QoS name is invalid
     */
    DSW_QOS_NAME_LENGHT_INVALID(50157201, "Invalid QoS policy name length"),
    /**
     * the count of qos out of limit
     */
    DSW_QOS_COUNT_OUT_OF_LIMIT(50157202, "The number of QoS policies exceeds the upper limit"),
    /**
     * the qos spec para is invalid
     */
    DSW_QOS_SPEC_INVALID(50157203, "Incorrect QoS policy parameter settings"),
    /**
     * QoS already exists.
     */
    DSW_QOS_ALREADY_EXISTS(50157204, "The QoS policy already exists"),
    /**
     * QoS not exist
     */
    DSW_QOS_NOT_EXISTS(50157205, "The QoS policy does not exist"),
    /**
     * QoS malloc memory failed
     */
    DSW_QOS_MALLOC_MEM_FAILED(50157206, "Failed to apply for memory when creating the QoS policy"),
    /**
     * QoS malloc internal resource failed
     */
    DSW_QOS_MALLOC_INTERNAL_FAILED(50157207, "Failed to apply for internal resources when creating the QoS policy"),
    /**
     * QoS spec conflict
     */
    DSW_QOS_SPEC_CONFLICT(50157208, "Conflicted QoS policy items"),
    /**
     * the result of query is out of limit
     */
    DSW_QUERY_RESULT_OUT_OF_LIMIT(
            50157209,
            "The number of query results exceeds the maximum number of QoS policies that can be queried at a time"),
    /**
     * The relation of qos has already existed
     */
    DSW_QOS_RELATION_EXIST(50157210, "The QoS policy associations exist"),
    /**
     * Pool meta malloc mem failed
     */
    DSW_POOL_META_MALLOC_FAILED(50157211, "Failed to apply for memory used by metadata of resource pools"),
    /**
     * The pool has associated with qos
     */
    DSW_POOL_ASSOCIATED_WITH_QOS(50157212, "The resource pool is associated with a QoS policy"),
    /**
     * The volume has associated with qos
     */
    DSW_VOLUME_ASSOCIATED_WITH_QOS(50157213, "The volume is associated with a QoS policy"),
    /**
     * The pool is disassociated with qos spec
     */
    DSW_POOL_DISASSOCIATED_WITH_QOS(50157214, "The resource pool is not associated with a QoS policy"),
    /**
     * The volume is disassociated with qos
     */
    DSW_VOLUME_DISASSOCIATED_WITH_QOS(50157215, "The volume is not associated with a QoS policy"),
    /**
     * Query para error
     */
    DSW_QOS_QUERY_PARA_ERROR(50157216, "Incorrect parameters"),

    /**
     * The type of the volume with qos is not normal
     */
    DSW_TYPE_NOT_NORMAL(50157218, "A bitmap volume cannot be associated with a QoS policy"),

    /**
     * The volume has associated with qos
     */
    DSW_SNAP_ASSOCIATED_WITH_QOS(50157219, "The snapshot is associated with a QoS policy"),

    /**
     * The volume is disassociated with qos
     */
    DSW_SNAP_DISASSOCIATED_WITH_QOS(50157220, "The snapshot is not associated with a QoS policy"),

    /**
     * 升级中不支持create / update
     */
    DSW_QOS_UPGRADING(50157223, "Temporarily unsupport create or update qos as system in upgrading"),

    /**
     * 创快照失败回滚成功
     */
    DSW_ERROR_CREATE_SNAP_ABORT(50157300, "Create snapshot failed and abort success"),

    /**
     * 创快照失败回滚超时
     */
    DSW_ERROR_CREATE_SNAP_ABORT_TMOUT(50157301, "Create snapshot failed and abort timeout"),

    /**
     * 创快照成功且仍在后台运行
     */
    DSW_ERROR_CREATE_SNAP_COMMIT_TMOUT(50157302, "Create snapshot timeout and still do background"),

    /**
     * The VBS node does not belong to the cluster
     */
    DSW_ERROR_VBS_NOT_IN_CLUSTER(50158000, "The VBS node does not belong to the cluster"),

    /**
     * The WWN obtained by the VBS node is incorrec
     */
    DSW_ERROR_VBS_SYSTEN_WWN_ERROR(50158001, "The WWN obtained by the VBS node is incorrect"),

    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_ERROR_VBS_GET_MDC_INFO_FAILED(50158002, "VBS start failed due to an incorrect response message from the MDC"),

    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_ERROR_VBS_TREEID_NOT_EXIST(50158003, "Tree with specific id not exist"),

    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_VBS_KMS_NOT_EXIST(50150026, "KMS not exist"),

    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_VBS_KMS_CANNOT_CONNECT(50150027, "KMS can not connect"),

    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_VBS_KMS_REQUEST_TIMEOUT(50150028, "KMS request TIMEOUT"),

    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_VBS_IAM_NOT_EXIST(50150029, "IAM not exist"),
    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_VBS_IAM_CANNOT_CONNECT(50150030, "IAM can not connect"),
    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_VBS_IAM_REQUEST_TIMEOUT(50150031, "IAM request TIMEOUT"),
    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_VBS_CERTIFICATE_OR_TOKEN_ERROR(50150032, "CERTIFICATE OR TOKEN ERROR"),
    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_VBS_CREATE_ENCRYPTED_VOL_BEYOND_MAX(50150033, "ENCRYPTED VOL BEYOND MAX"),
    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_VBS_DUPLICATE_VOLUME_ENCRYPT_FLAG_MISMATCH(50150034, "DUPLICATE VOLUME ENCRYPT FLAG MISMATCH"),
    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_VBS_DUPLICATE_VOLUME_CMKID_MISMATCH(50150035, "DUPLICATE VOLUME CMKID MISMATCH"),
    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_VBS_ENCRYPT_METADATA_NOT_EXIST(50150036, "VBS ENCRYPT METADATA NOT EXIST"),
    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_VBS_RESPONSE_FROM_SERVER_IS_NULL(50150037, "ENCRYPT RES FROM SERVER IS FULL"),
    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_VBS_ENCRYPT_METADATA_ALLOC_FAILED(50150038, "ENCRYPT METADATA ALLOC FAILED"),
    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_VBS_ENCRYPT_CONFIG_CHANGE_ERROR(50150039, "ENCRYPT CONFIG CHANGE ERROR"),
    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_VBS_ENCRYPT_CONFIG_CHANGE_KMC_ERROR(50150040, "ENCRYPT CONFIG CHANGE KMC ERROR"),
    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_VBS_ENCRYPT_VERIFY_ID_ERROR(50150041, "ENCRYPT VERIFY ID ERROR"),
    /**
     * The VBS node failed to start due to an incorrect response message from the MDC node
     */
    DSW_VBS_ENCRYPT_MESSAGE_TOKENS_ERROR(50150042, "ENCRYPT MESSAGE TOKENS ERROR"),
    /**
     * Query volume batch page size exceed max
     */
    DSW_ERROR_VBS_QUERY_VOLUME_BATCH_EXCEED_MAX(50150025, "Query volume batch page size exceed max,allow 65000"),

    /**
     * dr retcode
     * The VBS did not install Rep service
     */
    DSW_VBS_UNLOAD_SPLITTER_SERVICES(50150057, "VBS do not install the replication services"),

    /**
     * Query volume batch page size exceed max
     */
    DSW_ERROR_MEM_ALLOC_FAILED(50240000, "The two phase transaction allocate memory fails"),

    /**
     * Query volume batch page size exceed max
     */
    DSW_ERROR_ALLOC_VBS_TX_ID_FAIL(50240001, "The two phase transaction allocate id fails"),

    /**
     * Query volume batch page size exceed max
     */
    DSW_ERROR_OUT_OF_CONCURRENCY(50240002, "The two phase transaction concurrency number reaches the max limit"),

    /**
     * Query volume batch page size exceed max
     */
    DSW_ERROR_NO_ENOUGH_TX_RESOURCE(50240003, "Transaction resource is not enough"),

    /**
     * Query volume batch page size exceed max
     */
    DSW_ERROR_TX_ID_EXIST(50240004, "Transaction with specific id already exists"),

    /**
     * Query volume batch page size exceed max
     */
    DSW_ERROR_TX_ID_NOT_EXIST(50240005, "Transaction with specific id not exist"),

    /**
     * Rollback snapshot snap and vol in different pools
     */
    DSW_VBS_ROLLBACK_SNAP_VOLUME_IS_NOT_THE_SAME_POOL(50151218, "Volume and snapshot are in different pools"),
    /**
     * Rollback snapshot snap and vol in different trees
     */
    DSW_VBS_ROLLBACK_SNAP_VOLUME_IS_NOT_THE_SAME_TREE(50151219, "Volume and snapshot are in different trees"),
    /**
     * Rollback snapshot snap type invalid
     */
    DSW_VBS_ROLLBACK_SNAP_SNAP_TYPE_INVALID(50151220, "Snapshot type is invalid"),
    /**
     * Rollback snapshot vol type invalid
     */
    DSW_VBS_ROLLBACK_SNAP_VOLUME_TYPE_INVALID(50151221, "Volume type is invalid"),
    /**
     * Rollback snapshot iscsi lun not support
     */
    DSW_VBS_ROLLBACK_SNAP_ISCSI_LUN_NOT_SUPPORT(50151222, "iscsi lun is not support"),
    /**
     * 系统中没有卷信息存在
     */
    NO_VOLUME_EXIST(51010013, "No volume exist"),

    /**
     * 系统中没有快照信息存在
     */
    NO_SNAPSHOT_EXIST(51010014, "No snapshot exist"),

    /**
     * 系统中没有存储资源池存在
     */
    NO_POOL_EXIST(51010015, "No storage pool exist"),

    /**
     * 命令不支持
     */
    UNSUPPORTED_COMMAND(51010016, "Command not support"),

    /**
     * 系统中没有差量位图卷信息存在
     */
    NO_BITMAP_VOLUME_EXIST(51010017, "No bitmap volume exist"),

    /* agent 错误码 */
    /**
     * agent 内部错误
     */
    AGENT_UNKNOWN_ERROR(50500000, "agent unknown error"),

    /**
     * AGENT不支持此命令
     */
    AGENT_ERR_CMD_NOT_SUPPORT(50500001, "agent not support the command"),

    /**
     * agent 传入参数错误
     */
    AGENT_PARAMETER_ERROR(50500002, "agent parameter error"),

    /**
     * Failed to open the file
     */
    AGENT_ERR_OPEN_FILE(50500003, "Failed to open the file"),

    /**
     * Failed to parse the request
     */
    AGENT_ERR_ANALYZE_REQ_HDR(50500004, "Failed to parse the request"),

    /**
     * Failed to execute shell scripts
     */
    AGENT_ERR_RUN_SHELL(50500005, "Failed to execute shell scripts"),

    /**
     * agent 网络连接参数错误
     */
    AGENT_SEND_MSG_INCRRECT(50500008, "agent send message incrrect"),

    /**
     * agent 网络连接错误
     */
    AGENT_CONNECT_ERROR(50500009, "agent connection error"),

    /**
     * agent 发送消息失败
     */
    AGENT_SEND_MSG_FAILED(50500010, "agent send message failed"),

    /**
     * agent 接受消息失败
     */
    AGENT_REV_MSG_FAILED(50500011, "agent recieve message failed"),

    /**
     * Failed to load the KO module
     */
    AGENT_ERR_KO_LOAD(50500012, "Failed to load the KO module"),

    /**
     * Failed to allocate memory to the FSA node
     */
    AGENT_ERR_MALLOC_FAIL(50500013, "Failed to allocate memory to the FSA node"),

    /**
     * FSA failed to write cluster files
     */
    AGENT_ERR_WRITE_DSW_CLUSTER_FILE_FAIL(50500014, "FSA failed to write cluster files"),

    /**
     * FSA failed to create the message header for communicating with the MDC
     */
    AGENT_ERR_SET_MSG_HEAD(50500015, "FSA failed to create the message header for communicating with the MDC"),

    /**
     * Invalid IP address format
     */
    AGENT_ERR_INVALID_IP_FORMAT(50500016, "Invalid IP address format"),

    /**
     * Failed to obtain an IP address
     */
    AGENT_ERR_GET_IP_FAIL(50500017, "Failed to obtain an IP address"),

    /**
     * Starting the process timed out
     */
    AGENT_ERR_START_PROCESS_TIMEOUT(50500018, "Starting the process timed out"),

    /**
     * The process or configuration file already exists on the server
     */
    AGENT_ERR_CHECK_NODE(50501001, "The process or configuration file already exists on the server"),

    /**
     * FSA failed to clear the node
     */
    AGENT_ERR_CLEAR_NODE(50501002, "FSA failed to clear the node"),

    /**
     * Failed to check vbs id and port
     */
    AGENT_ERR_CHECK_VBS_ID_PORT(50501003, "Failed to check vbs id and port"),

    /**
     * Failed to start the ZooKeeper process
     */
    AGENT_ERR_ADD_ZK(50501004, "Failed to start the ZooKeeper process"),

    /**
     * Failed to start the MDC process
     */
    AGENT_ERR_ADD_MDC(50501005, "Failed to start the MDC process"),

    /**
     * Failed to start the OSD
     */
    AGENT_ERR_ADD_OSD(50501006, "Failed to start the OSD"),

    /**
     * Failed to start the VBS
     */
    AGENT_ERR_ADD_VBS(50501007, "Failed to start the VBS"),

    /**
     * Failed to write the ZooKeeper configuration file
     */
    AGENT_ERR_WRITE_ZK_FILE(50501008, "Failed to write the ZooKeeper configuration file"),

    /**
     * Failed to write the FSA configuration file to the system
     */
    AGENT_ERR_WRITE_DSWARE_FILE(50501009, "Failed to write the FSA configuration file to the system"),

    /**
     * agent 读取配置文件失败
     */
    AGENT_READ_CONF_FAILED(50501010, "agent read config failed"),

    /**
     * The FSA node fails to query information about the active MDC node
     */
    AGENT_ERR_QRY_PRIMARY_MDC(50501011, "The FSA node fails to query information about the active MDC node"),

    /**
     * FSA failed to communicate with the MDC
     */
    AGENT_ERR_MDC_CON(50501012, "FSA failed to communicate with the MDC"),

    /**
     * Failed to monitor the process
     */
    AGENT_ERR_PROCESS_MONITOR(50501013, "Failed to monitor the process"),

    /**
     * Failed to stop the process
     */
    AGENT_ERR_PROCESS_STOP(50501014, "Failed to stop the process"),

    /**
     * Failed to obtain the storage IP address
     */
    AGENT_ERR_GET_NODE_IP(50501015, "Failed to obtain the storage IP address"),

    /**
     * Failed to query disk information
     */
    AGENT_ERR_INQUIRY_DISK_INFO(50501016, "Failed to query disk information"),

    /**
     * Failed to query the process information
     */
    AGENT_ERR_QUERY_PROCESS_INFO(50501017, "Failed to query the process information"),

    /**
     * VFS ID has been used
     */
    AGENT_ERR_ADD_VFS_ID_IS_USED(50501018, "VFS ID has been used"),

    /**
     * VFS port has been used
     */
    AGENT_ERR_ADD_VFS_PORT_IS_USED(50501019, "VFS port has been used"),

    /**
     * Failed to change the management IP address
     */
    AGENT_ERR_CHANGE_MANAGER_IP(50501020, "Failed to change the management IP address"),

    /**
     * Failed to delete the system environment AGENTta
     */
    AGENT_ERR_CHLEAR_SYS_ENV(50501021, "Failed to delete the system environment AGENTta"),

    /**
     * Failed to disable the hard disk cache functio
     */
    AGENT_ERR_CLOSE_CACHE(50501024, "Failed to disable the hard disk cache function"),

    /**
     * Failed to start the dsware service
     */
    AGENT_ERR_PROCESS_START(50501025, "Failed to start the dsware service"),

    /**
     * The system contains illegal disks
     */
    AGENT_ERR_ILLEGAL_DISK(50501026, "The system contains illegal disks"),

    /**
     * Failed to start OpenSM
     */
    AGENT_ERR_ADD_OPENSM(50501027, "Failed to start OpenSM"),

    /**
     * Network operations failed
     */
    AGENT_ERR_NET_OP(50501028, "Network operations failed"),

    /**
     * Failed to read the ZK slot file
     */
    AGENT_ERR_READ_ZK_SLOT_FILE_FAIL(50501029, "Failed to read the ZK slot file"),

    /**
     * The ZK slot is invalid
     */
    AGENT_ERR_ZK_SLOT_ILLEGAL(50501030, "The ZK slot is invalid"),

    /**
     * The disk type is invali
     */
    AGENT_ERR_ILLEGAL_DISK_TYPE(50501031, "The disk type is invalid"),

    /**
     * Failed to monitor the disk
     */
    AGENT_ERR_DISK_MONITOR_FAIL(50501032, "Failed to monitor the disk"),

    /**
     * Failed to configure NTP
     */
    AGENT_ERR_SET_NTP_FAIL(50501033, "Failed to configure NTP"),

    /**
     * Failed to forcibly synchronize the NTP time settings
     */
    AGENT_ERR_FORCE_NTP_FAIL(50501034, "Failed to forcibly synchronize the NTP time settings"),

    /**
     * Failed to set the system parameters
     */
    AGENT_ERR_SET_SYS_PARA(50501035, "Failed to set the system parameters"),

    /**
     * Network device is not existed
     */
    AGENT_ERR_NET_DEVICE_NOT_EXIST(50501036, "Network device is not existed"),

    /**
     * The OS is not supported
     */
    AGENT_ERR_NOT_SUPPORTED_OS_TYPE(50501037, "The OS is not supported"),

    /**
     * Can not find port according to PCI bus
     */
    AGENT_ERR_NOT_FIND_PORT_BY_PCI_BUS(50501038, "Can not find port according to PCI bus"),

    /**
     * Can not find port according to MAC address
     */
    AGENT_ERR_NOT_FIND_PORT_BY_MAC(50501039, "Can not find port according to MAC address"),

    /**
     * IP conflict
     */
    AGENT_ERR_NET_IP_CONFLICT(50501040, "IP conflict"),

    /**
     * Port send rate exceeds system limi
     */
    AGENT_ERR_NET_TX_LIMIT_EXCEED_SYSTEM_LIMIT(50501041, "Port send rate exceeds system limit"),

    /**
     * The server does not contain the specified IP address
     */
    AGENT_ERR_IP_NOT_EXIST_IN_SERVER(50501042, "The server does not contain the specified IP address"),

    /**
     * Restart VBS process failed
     */
    AGENT_ERR_RESTART_VBS_FAILED(50501043, "Restart VBS process failed"),

    /**
     * Failed to obtain the uplink port policy on the node
     */
    AGENT_ERR_CONFIG_PORT_RULE_FAILED(50501044, "Failed to obtain the uplink port policy on the node"),

    /**
     * VLAN rules cannot be modified
     */
    AGENT_ERR_VLANID_NOT_ALLOWED_TO_MODIFY(50501045, "VLAN rules cannot be modified"),

    /**
     * The logical network port is not configured
     */
    AGENT_ERR_LOGIC_NETWORK_CARD_NOT_CREATED(50501046, "The logical network port is not configured"),

    /**
     * This operation is not allowed in this scenario
     */
    AGENT_ERR_SCENE_IS_NOT_SUPPORTED(50501047, "This operation is not allowed in this scenario"),

    /**
     * Failed to stop the MDC process
     */
    AGENT_ERR_STOP_MDC_PROCESS(50501048, "Failed to stop the MDC process"),

    /**
     * Failed to back up files to the USB device
     */
    AGENT_ERR_REFRESH_FILE_TO_USB(50501049, "Failed to back up files to the USB device"),

    /**
     * Failed to save AGENTta on the SSD card to the hard disk
     */
    AGENT_ERR_FLUSH_SSD_CACHE(50501050, "Failed to save AGENTta on the SSD card to the hard disk"),

    /**
     * Failed to query the progress of saving SSD cache AGENTta
     */
    AGENT_ERR_QUERY_FLUSH_SSD_CACHE(50501051, "Failed to query the progress of saving SSD cache AGENTta"),

    /**
     * Failed to query disks
     */
    AGENT_ERR_IOCTL_FAIL(50501052, "Failed to query disks"),

    /**
     * Config RoCE network failed
     */
    AGENT_ERR_NET_ROCE_OP(50501053, "Config RoCE network failed"),

    /**
     * The RoCE NIC strategy is not supported
     */
    AGENT_ERR_NOT_SUPPORT_STRATEGY(50501054, "The RoCE NIC strategy is not supported"),

    /**
     * The BOND device already exists.The standby device cannot be modified
     */
    AGENT_ERR_NET_DEVICE_HAVED_EXISTED(50501055, "The BOND device already exists.The standby device cannot modify"),

    /**
     * The BOND mode cannot be changed
     */
    AGENT_ERR_BOND_STRATEGY_CAN_NOT_MODIFIED(50501056, "The BOND mode cannot be changed"),

    /**
     * The configuration file of the time zone does not exist
     */
    AGENT_ERR_TZ_CONF_INVALID(50501057, "The configuration file of the time zone does not exist"),

    /**
     * The InfiniBand NIC is not in position
     */
    AGENT_ERR_IB_NIC_NOT_EXISTED(50501100, "The InfiniBand NIC is not in position"),

    /**
     * Failed to config the ports access control rules
     */
    AGENT_ERR_CONFIG_PORT_ACL_FAILED(50501101, "Failed to config the ports access control rules"),

    /**
     * The NVDIMM is faulty
     */
    AGENT_ERR_NVDIMM_ALARM(50502000, "The NVDIMM is faulty"),

    /**
     * The NVIDIMM is unavailable
     */
    AGENT_ERR_NVDIMM_NOT_READY(50502001, "The NVIDIMM is unavailable"),

    /**
     * Failed to check the NVDIMM alarms
     */
    AGENT_ERR_NVDIMM_CHK_FAIL(50502002, "Failed to check the NVDIMM alarms"),

    /**
     * Failed to start file client
     */
    AGENT_ERR_ADD_VFS(50504000, "Failed to start file client"),

    /**
     * No file client is available on the current node
     */
    AGENT_ERR_NO_AVALIABLE_VFS(50504001, "No file client is available on the current node"),

    /**
     * The correct NFS or XFS is not installed
     */
    AGENT_ERR_CHECK_NFS_XFS(50504002, "The correct NFS or XFS is not installed"),

    /**
     * Failed to check the SSD status
     */
    AGENT_ERR_QUERY_SSD_STATUS(50505001, "Failed to check the SSD status"),

    /**
     * The SSD is faulty
     */
    AGENT_ERR_SSD_BATTERY(50505002, "The SSD is faulty"),

    /**
     * vbs client unknow error
     */
    VBS_CLIENT_UNKNOW_ERROR(50510000, "vbs client unknow error"),

    /**
     * log path of vbs client error
     */
    VBS_CLIENT_LOG_FILE_PATH_ERROR(50510001, "log path of vbs client error"),

    /**
     * vbs client read parameta in config file error
     */
    VBS_CLIENT_LOAD_CONFIG_FILE_ERROR(50510002, "vbs client read parameta in config file error"),

    /**
     * vbs client alloc memory failed
     */
    VBS_CLIENT_ALLOC_MEM_FAILED(50510003, "vbs client alloc memory failed"),

    /**
     * vbs client connect vbs failed
     */
    VBS_CLIENT_CONNECT_ERROR(50510009, "vbs client connect vbs failed"),

    /**
     * vbs client send message failed
     */
    VBS_CLIENT_SEND_MESSAGE_ERROR(50510010, "vbs client send message failed"),

    /**
     * vbs client receive message failed
     */
    VBS_CLIENT_RECEIVE_MESSAGE_ERROR(50510011, "vbs client receive message failed"),

    /**
     * command format of vbs client error
     */
    VBS_CLIENT_CMD_FORMAT_ERROR(50510012, "command format of vbs client error"),

    /**
     * config file name length of vbs error
     */
    VBS_CONF_FILE_LENGTH_ERROR(50510013, "config file name length of vbs error"),

    /**
     * config file of vbs is not exist
     */
    VBS_CONF_FILE_NOT_EXIST(50510014, "config file of vbs is not exist"),

    /**
     * The iscsi_portal does not exist
     */
    AGENT_ERR_INAVALIABLE_PORTAL(50542001, "The iscsi_portal does not exist"),

    /**
     * Failed to add the iscsi_portal to the configuration file
     */
    AGENT_ERR_ADD_ISCSI_PORTAL_TO_CONF(50542004, "Failed to add the iscsi_portal to the configuration file"),

    /**
     * Failed to delete the iscsi_portal from the configuration file
     */
    AGENT_ERR_DEL_ISCSI_PORTAL_TO_CONF(50542005, "Failed to delete the iscsi_portal from the configuration file"),

    /**
     * Failed to query the iscsi_portal in the configuration file
     */
    AGENT_ERR_QRY_ISCSI_PORTAL_FROM_CONF(50542006, "Failed to query the iscsi_portal in the configuration file"),

    /**
     * The VBS node failed to add the iscsi_portal
     */
    AGENT_ERR_ADD_ISCSI_PORTAL_TO_VBS(50542007, "The VBS node failed to add the iscsi_portal"),

    /**
     * The VBS node failed to query the iscsi_portal
     */
    AGENT_ERR_GET_ISCSI_FROM_VBS(50542008, "The VBS node failed to query the iscsi_portal"),

    /**
     * The iSCSI is disabled
     */
    AGENT_ERR_ISCSI_SWITCH_CLOSE(50542009, "The iSCSI is disabled"),

    /**
     * The node does not have the VBS process that is created by the requested FSM
     */
    AGENT_ERR_NO_VBS_EXIST_FOR_FSM(50542012, "The node does not have VBS process created by the requested FSM"),

    /**
     * iSCSI has already been configured for all the VBS processes on the node
     */
    AGENT_ERR_NO_VBS_WHICH_NOT_CONFIG_ISCSI(50542013, "iSCSI has already been configured for all VBS on the node"),

    /**
     * Failed to notify vbs to modify iscsi switch
     */
    AGENT_ERR_SWITCH_ISCSI_UPAGENTTE_FAILED(50542014, "Failed to notify vbs to modify iscsi switch"),

    /**
     * Failed to notify vbs to modify iscsi switch partly
     */
    AGENT_ERR_SWITCH_ISCSI_PART_SUCCESS(50542015, "Failed to notify vbs to modify iscsi switch partly"),

    /**
     * Failed to obtain the storage pool configuration information
     */
    AGENT_ERR_NO_HAVE_POOL_INFO(50543013, "Failed to obtain the storage pool configuration information"),

    /**
     * Failed to notify service processes of MDC configuration changes
     */
    AGENT_ERR_NOTIFY_PROCESS_FAIL(50543014, "Failed to notify service processes of MDC configuration changes"),

    /**
     * AGENT与VBS通信超时
     */
    AGENT_ERR_REC_TIMEOUT(50543015, "agent communicate VBS timeout"),

    /**
     * Failed to start the ZooKeeper, because no ZooKeeer partition is attached
     */
    AGENT_ERR_ADD_ZK_NEED_MOUNT_DISK(50543017, "ZooKeeper start failed, because no ZooKeeer partition is attached"),

    /**
     * Failed to create block storage client, vbs ID has been used
     */
    AGENT_ERR_ADD_VBS_ID_IS_USED(50543018, "Failed to create block storage client, vbs ID has been used"),

    /**
     * Failed to create block storage client, vbs port has been used
     */
    AGENT_ERR_ADD_VBS_PORT_IS_USED(50543019, "Failed to create block storage client, vbs port has been used"),

    /**
     * Failed to pre-flush AGENTta into disks
     */
    AGENT_ERR_PREFLUSH_SSD_CAHE_FAIL(50543022, "Failed to pre-flush AGENTta into disks"),

    /**
     * Failed to query the volume attaching result
     */
    AGENT_ERR_QUERY_ATTACH_VOLUME_FAIL(50543025, "Failed to query the volume attaching result"),

    /**
     * Failed to flush OSD metaAGENTta into disks
     */
    AGENT_ERR_FLUSH_OSD_METAAGENTTA_FAIL(50543026, "Failed to flush OSD metaAGENTta into disks"),

    /**
     * 无可用的VBS
     */
    AGENT_ERR_NO_AVALIABLE_VBS(50560010, "no avaliable vbs"),

    /* API 内部错误码 */
    /**
     * 内部错误
     */
    INTERNAL_ERROR(51010001, "Internal error"),

    /**
     * 封装消息失败
     */
    MAKE_MSG_ERROR(51010002, "Make message failed"),

    /**
     * 解析消息失败
     */
    PARSE_MSG_ERROR(51010003, "Parse message failed"),

    /**
     * 无此属性
     */
    NO_SUCH_ATTRIBUTE_ERROR(51010004, "No such attribute"),

    /**
     * 无此数据类型
     */
    NO_SUCH_TYPE_ERROR(51010005, "No such data type"),

    /**
     * 命名不合法
     */
    NAME_INVALID_ERROR(51010006, "Name invalid"),

    /**
     * IP不合法
     */
    IP_INVALID_ERROR(51010007, "IP invalid"),

    /**
     * 无返回值错误
     */
    NO_RESPONSE_ERROR(51010008, "No response from agent"),

    /**
     * 返回消息长度不正确
     */
    MSG_FROM_AGENT_INCORRECT(51010009, "Message from agent incorrect"),

    /**
     * 参数不合法
     */
    INVALID_PARAMETER(51010010, "Parameter invalid"),

    /**
     * 主机不可达
     */
    HOST_UNREACHABLE(51010011, "Host unreachable"),

    /**
     * 与agent通信异常
     */
    COMMUNICATE_AGENT_ERROR(51010012, "Connect to agent error"),

    /**
     * socket连接超时
     */
    SOCKET_CONNECT_TIMEOUT(51010018, "socket connect timeout"),

    /**
     * socket读超时
     */
    SOCKET_READ_TIMEOUT(51010019, "socket read timeout"),

    /**
     * SDI CLI operate error
     */
    SDI_CLI_OPERATE_ERROR(50550001, "sdi cli operate error"),

    /**
     * LLD CACHE flag error
     */
    LLD_CACHE_FLAG_ERROR(50151035, "lld cache flag error"),

    /**
     * LLD VOL image error
     */
    LLD_VOL_IMAGE_ERROR(50151036, "lld image size need less than or equal vol size"),

    /**
     * LLD IMAGE OFFSET  error
     */
    LLD_VOL_IMAGE_OFFSET_ERROR(50151037, "lld image offset error"),

    /**
     * LLD IMAGE meta not exist
     */
    LLD_VOL_IMAGE_META_NOT_EXIST(50151043, "lld image meta not exist"),

    /**
     * LLD image url size error
     */
    LLD_VOL_IMAGE_URL_SIZE_ERROR(50151044, "lld image URL size error"),

    /**
     * LLD image ID size error
     */
    LLD_VOL_IMAGE_ID_SIZE_ERROR(50151045, "lld image ID size error"),

    /**
     * LLD image  size error
     */
    LLD_VOL_IMAGE_SIZE_ERROR(50151046, "lld image ID size error"),

    /**
     * LLD get image info timeout
     */
    LLD_DGW_GET_IMAGE_INFO_TIMEOUT(50151047, "lld dgw get image info timeout"),

    /**
     * LLD get image size from IMS is error
     */
    LLD_DGW_GET_IMAGE_SIZE_FROM_IMS_ERROR(50151048, "lld dgw get image size from ims is error"),

    /**
     * LLD get image from img is error
     */
    LLD_DGW_GET_IMAGE_INFO_ERROR(50151049, "lld dgw get image info error"),

    /**
     * LLD image true size error
     */
    LLD_IMAGE_REAL_SIZE_ERROR(50151050, "image true size is error"),

    /**
     * LLD node cannot be deleted
     */
    LLD_NODE_DEL_DISABLED(50151051, "lld node cannot be deleted"),

    /**
     * LLD volume under lazyloading status
     */
    LLD_VOL_UNDER_LAZYLOADING(50151052, "lld volume is under lazyloading"),

    /**
     * LLD image number exceed maximum
     */
    LLD_IMAGE_MAP_REACH_MAX_NUM(50151053, "lld image number exceed maximum"),

    /**
     * LLD task number beyond max
     */
    LLD_TASK_NUMBER_EXCEED_MAXIMUM(50151054, "lld task number exceed maximum"),

    /**
     * LLD image not support encrypt
     */
    LLD_NOT_SUPPORT_ENCRYPT(50151055, "lld not support encrypt"),

    /**
     * LLD image not support encrypt
     */
    LLD_VOLUME_NUMBER_EXCEED_MAXIMUM(50151057, "lld volume number exceed maximum"),

    /**
     * dgw process is abnormal
     */
    LLD_DGW_PROCESS_ABNORMAL(50150063, "dgw process is abnormal"),

    /**
     * LLD switch is close
     */
    LLD_SWITCH_IS_CLOSE(50151101, "lld lazyloading switch is close"),

    /**
     * The internal source volume is under lazyloading
     */
    LLD_IMAGE_SRC_IS_LLD(50151065, "The internal source volume is under lazyloading"),

    /**
     * Parameters used to replace the original volume are inconsistent with the original volume's existing parameters
     */
    LLD_REPLACE_VOL_PARAMETER_INVALID(
            50151066,
            "Parameters used to replace the original volume are inconsistent with the original volume's existing"
                + " parameters"),

    /**
     * Set delete flag failed
     */
    LLD_SET_DELETE_FLAG_FAILED(50151067, "Set delete flag failed"),

    /**
     * The migration task already exists
     */
    OLMG_TASK_ALREADY_EXIST(50157500, "the migration task already exists"),

    /**
     * The migration task does not exist
     */
    OLMG_TASK_NOT_EXIST(50157501, "the migration task does not exist"),

    /**
     * The number of migration tasks reaches the upper limit
     */
    OLMG_TASK_NUM_EXCEED(50157502, "the number of migration tasks reaches the upper limit"),

    /**
     * The migration task status is being updated
     */
    OLMG_TASK_PERSISTING(50157503, "the migration task status is being updated"),

    /**
     * The volume or the snapshot's source volume is a shared volume, and shared volumes cannot be migrated
     */
    OLMG_VOL_SNAP_IS_SHARED(
            50157504,
            "the volume or the snapshot's source volume is a shared volume, and shared volumes cannot be migrated"),

    /**
     * The number of volume snapshots that can be migrated exceeds the upper limit
     */
    OLMG_VOL_SNAP_NUM_BEYOUND_MAX(
            50157505, "the number of volume snapshots that can be migrated exceeds the upper limit"),

    /**
     * The volume metadata generated during migration exceeds the maximum quantity allowed
     */
    OLMG_METADATA_BEYOUND_MAX(
            50157506, "the volume metadata generated during migration exceeds the maximum quantity allowed"),

    /**
     * The volume cannot be migrated as the parent-child relationship of the volume has branches
     */
    OLMG_BRANCH_TOO_MUCH(
            50157507, "the volume cannot be migrated as the parent-child relationship of the volume has branches"),

    /**
     * The volume metadata or volume's snapshot metadata is not in the normal status
     */
    OLMG_NODE_STATUS_ABNORMAL(
            50157508, "the volume metadata or volume's snapshot metadata is not in the normal status"),

    /**
     * Failed to prepare resources for the volume migration
     */
    OLMG_CLEAR_RESOURCE_FAILED(50157509, "failed to prepare resources for the volume migration"),

    /**
     * The volume reservation metadata is being updated. Try again later
     */
    OLMG_PR_META_IN_UPDATING(50157510, "the volume reservation metadata is being updated. Try again later"),

    /**
     * The migration task is batch saving the metadata persistently
     */
    OLMG_BATCH_SYNC_METADATA(50157511, "the migration task is batch saving the metadata persistently"),

    /**
     * The migration task is being migrated back or rolled back
     */
    OLMG_TASK_IN_BACK_PHASE(50157512, "the migration task is being migrated back or rolled back"),

    /**
     * The parent-child relationship of the volume exists in the target storage pool
     */
    OLMG_TREE_IN_DST_POOL(50157513, "the parent-child relationship of the volume exists in the target storage pool"),

    /**
     * The lazyloaded metadata in the migration task is abnormal
     */
    OLMG_LLD_TASK_META_ERROR(50157514, "the lazyloaded metadata in the migration task is abnormal"),

    /**
     * Dynamic set migration switch type error
     */
    DSW_DYNAMIC_SET_OLMG_SWITCH_TYPE_ERROR(50157515, "dynamic set migration switch type error"),

    /**
     * The migration switch is closed
     */
    DSW_VBS_OLMG_SWITCH_CLOSED(50157516, "the migration switch is closed"),

    /**
     * Live migration is not supported because the volume is in an EC pool or the target pool is an EC pool
     */
    DSW_VBS_OLMG_VOLUME_IN_EC_POOL(
            50157519,
            "live migration is not supported because the volume is in an EC pool or the target pool is an EC pool"),

    /**
     * Set key change switch error
     */
    DSW_VBS_SET_KEY_CHANGE_SWITCH_ERROR(50157521, "set key change switch error"),

    /**
     * Key change switch is closed
     */
    DSW_VBS_ERROR_KEY_CHANGE_SWITCH_CLOSED(50157522, "key change switch is closed"),

    /**
     * Live migration switch is open
     */
    DSW_VBS_ERROR_OLMG_SWITCH_OPENED(50157523, "live migration switch is open"),

    /**
     * vg alloc mem failed
     */
    DSW_VG_ALLOC_MEM_FAILED(50157420, "vg alloc mem failed"),

    /**
     * vol group is not exist
     */
    DSW_VBS_VOL_GROUP_NOT_EXIST(50157421, "vol group is not exist"),

    /**
     * vol group name is invail
     */
    DSW_VBS_VOL_GROUP_NAME_INVALID(50157422, "vol group name is invail"),

    /**
     * vol group is already associated with qos
     */
    DSW_QOS_VOL_GROUP_ASSOCIATION_ALREADY_EXISTED(50157423, "vol group is already associated with qos"),

    /**
     * vol group is not associated with the qos
     */
    DSW_QOS_VOL_GROUP_ASSOCIATION_NOT_EXISTED(50157424, "vol group is not associated with the qos"),

    /**
     * vol group param error
     */
    DSW_QOS_VG_PARAM_ERROR(50157425, "vol group param error"),

    /**
     * vol group num is out of limit
     */
    DSW_QOS_VG_QUERY_SIZE_OUT_OF_LIMIT(50157426, "vol group num is out of limit"),

    /**
     * vol group already belong some vol group type
     */
    DSW_VG_ALREADY_BELONG_SOME_VGT(50157427, "vol group already belong some vol group type"),

    /**
     * vol group already belong some target vbs
     */
    DSW_VG_ALREADY_BELONG_SOME_NID(50157428, "vol group already belong some target vbs"),

    /**
     * vol num is overflow in vol group
     */
    DSW_VOL_NUM_IN_VG_OVERFLOW(50157429, "vol num is overflow in vol group"),

    /**
     * the qos already relate vg
     */
    DSW_VBS_QOS_ALREADY_RELATE_VOL_GROUP(50157430, "the qos already relate vg"),

    /**
     * vol group num is exceed
     */
    DSW_VBS_VOL_GROUP_NUM_EXCEED(50157431, "vol group num is exceed"),

    /**
     * vol group type num is exceed
     */
    DSW_VBS_VGT_NUM_EXCEED(50157432, "vol group type num is exceed"),

    /**
     * vol group type is not exist
     */
    DSW_VBS_VGT_NOT_EXIST(50157433, "vol group type is not exist"),

    /**
     * vol group is already exist
     */
    DSW_VBS_VOL_GROUP_EXIST(50157434, "vol group is already exist"),

    /**
     * vol group type is already exist
     */
    DSW_VBS_VGT_EXIST(50157435, "vol group type is already exist"),

    /**
     * some vol group have related with the vol group type
     */
    DSW_VBS_VGT_ALREADY_RELATION(50157436, "some vol group have related with the vol group type"),

    /**
     * The vol group switch is close or pre close
     */
    DSW_VBS_VOL_GROUP_SWITCH_IS_CLOSE(50157439, "The vol group switch is close or pre close"),

    /**
     * vol group num is overflow in target
     */
    DSW_VG_NUM_IN_NID_OVERFLOW(50157442, "vol group num is overflow in target"),

    /**
     * Insufficient storage pool performance
     */
    DSW_VBS_LLD_VOL_ABILITY_SHORT(50151108, "Insufficient storage pool performance"),

    /**
     * The source image of lazyloading volume is error
     */
    DSW_VBS_LLD_SRC_IMAGE_ERROR(50240202, "The source image of lazyloading volume is error"),

    /**
     * lld or imgcp task is not exist
     */
    DSW_VBS_LLD_TASK_NOT_EXIST(50150062, "lld or imgcp task is not exist."),

    /**
     * this vol is doing image_copy can't be attached
     */
    DSW_VBS_IMGCP_VOL_IN_TASK_WHEN_ATTACH(50158050, "this vol is doing image_copy can't be attached."),

    /**
     * abort image copy task fail
     */
    DSW_VBS_IMGCP_ABORT_FAIL(50158051, "abort image copy task fail."),

    /**
     * imgcp task is not exist
     */
    DSW_VBS_IMGCP_TASK_NOT_EXIST(50158052, "imgcp task is not exist."),

    /**
     * duplicate vol have same name
     */
    DSW_VBS_DUPLICATE_VOL_HAVE_SAME_NAME(50152904, "the src volume and dst volume have the same name."),

    /**
     * lld or imgcp task is exist
     */
    DSW_VBS_LLD_TASK_EXIST(50151056, "lld or imgcp task is exist."),

    /**
     * The volume in image copy status
     */
    VOLUME_IS_IN_IMGCP(50240209, "The volume is in image copy status"),

    /**
     * The param is invalid
     */
    DSW_VBS_IMGCP_PARAM_INVALID(50240210, "The parameter is invalid"),

    /**
     * the node status is abnormal
     */
    DSW_VBS_IMGCP_NODE_STATUS_ABNORMAL(50240211, "The node status is abnormal"),

    /**
     * The snapshot is exporting the image
     */
    DSW_VBS_SNAP_IS_IN_EXPORT_IMAGE(50240216, "The snapshot is exporting the image"),

    /**
     * The image does't exist
     */
    DSW_VBS_IMAGE_NOT_EXIST(50151104, "The image does't exist"),

    /**
     * qos_group num exceed
     */
    DSW_QOS_GROUP_NUM_EXCEED(50157217, "qos_group num exceed"),

    /**
     * The capacity QoS can not be a qos group
     */
    DSW_QOS_GROUP_CAN_NOT_BE_CAP_QOS(50157230, "The capacity QoS can not be a qos group"),

    /**
     * associate qos or qos_param invalid
     */
    DSW_QOS_GROUP_PARAM_INVALID(50157229, "associate qos or qos_group param invalid"),

    /**
     * NPST volume QoS associate number exceed
     */
    DSW_NPST_NUM_EXCEED(50157224, "NPST volume QoS associate number exceed"),

    /**
     * VBS in single cluster mode
     */
    DSW_VBS_IN_SINGLE_CLUSTER_MODE(50240230, "VBS in single cluster mode"),

    /**
     * Multicluster attach meta switch not close
     */
    DSW_VBS_MULTI_ATTACH_META_SWITCH_NOT_CLOSE(50240231, "Multicluster attach meta switch not close"),

    /**
     * The volume not in bp white list
     */
    DSW_VBS_VOLUME_NOT_IN_BP_WHITE_LIST(50240232, "The volume not in bp white list!"),

    /**
     * The volume not in bp white list
     */
    DSW_VBS_BP_WHITE_LIST_SWITCH_CLOSE(50240233, "The bp white list switch close!"),

    /*
     * /**
     * 未知错误
     */
    UNKNOWN_FAILURE(51011000, "Unknown error");

    private static final Logger LOGGER = LoggerFactory.getLogger(DSwareErrorCode.class);

    /**
     * 错误码基数
     */
    private static final int ERROR_BASE = 50000000;

    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 错误码描述
     */
    private String description;

    DSwareErrorCode(int errorCode, String description) {
        this.setErrorCode(errorCode);
        this.setDescription(description);
    }

    /**
     * 普通错误码转换
     *
     * @param errorCode 原始错误码
     * @return  DSwareErrorCode
     */
    public static DSwareErrorCode toEnum(int errorCode) {
        int tempCode = Math.abs(errorCode);

        if (tempCode < ERROR_BASE) {
            tempCode += ERROR_BASE;
        }

        DSwareErrorCode[] errorCodes = DSwareErrorCode.values();
        for (DSwareErrorCode code : errorCodes) {
            if (code.getErrorCode() == tempCode) {
                return code;
            }
        }

        LOGGER.error("Unknown error, errorCode:" + errorCode);
        return UNKNOWN_FAILURE;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    private void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    /**
     * 重载toString方法
     *
     * @return  String
     */
    public String toString() {
        return "ErrorCode=" + this.errorCode + "(" + this.description + ")";
    }
}
