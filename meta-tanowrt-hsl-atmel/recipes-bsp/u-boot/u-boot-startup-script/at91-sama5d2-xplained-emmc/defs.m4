#
# SPDX-License-Identifier: MIT
# SPDX-FileCopyrightText: 2022 Tano Systems LLC
#
# U-Boot startup script definitions
#

# Console parameters
define(CONSOLE_DEV, `ttyS0')
define(CONSOLE_BAUDRATE, `${baudrate}')

# Init commands
define(NORMAL_INIT, `
	echo "Board name: $board_name";')

# Normal boot settings
define(NORMAL_LOAD, `')
define(NORMAL_LOAD_KERNEL, `
	mmc dev 0 || exit;
	ext4load mmc 0:${kernel_part} ${loadaddr} fitImage || exit;')
define(NORMAL_KERNEL_ROOT_ARGS, `\
	root=/dev/mmcblk0p${rootfs_part} ro\
	rootfstype=squashfs\
	rootwait\
	rootfs_partition=${rootfs_part}\
	rootfs_volume=${rootfs_part}')
define(SWU_SETUP_ACTIVE_SYSTEM_A, `kernel_part=2; rootfs_part=3;')
define(SWU_SETUP_ACTIVE_SYSTEM_B, `kernel_part=5; rootfs_part=6;')
define(SWU_SETUP_ACTIVE_SYSTEM_BOTH, `')

# Settings for factory installation media startup script
define(FACTORY_LOAD, `')
define(FACTORY_INIT, NORMAL_INIT)
define(FACTORY_LOAD_KERNEL, `
	mmc dev 1 || exit;
	fatload mmc 1:1 ${loadaddr} fitImage || exit;')
define(FACTORY_KERNEL_ROOT_ARGS, `\
	root=/dev/ram\
	rootfstype=squashfs\
	rootwait=1')
