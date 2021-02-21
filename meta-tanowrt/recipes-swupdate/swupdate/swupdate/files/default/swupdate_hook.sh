#!/bin/sh
# Copyright (C) 2021 Tano Systems LLC. All Rights Reserved.
# SPDX-License-Identifier: MIT

[ "${SWU_FACTORY_INSTALL}" = "1" ] && exit 0

[ -f /usr/lib/swupdate/swupdate.sh ] || exit 1

. /usr/lib/swupdate/swupdate.sh

case "$1" in
	preupdate)
		if [ "${SWU_SOFTWARE_SET}" = "tanowrt" ] && \
		   [ "${SWU_RUNNING_MODE}" = "system-a" ] || \
		   [ "${SWU_RUNNING_MODE}" = "system-b" ]; then
			echo 1 > "${SWU_REBOOT_STATE_FILE}"
			echo "Rebooting enabled"
		else
			echo 0 > "${SWU_REBOOT_STATE_FILE}"
			echo "Rebooting disabled"
		fi
		;;

	postupdate-success)
		REBOOT=$(cat "${SWU_REBOOT_STATE_FILE}" 2> /dev/null || echo 0)
		if [ "${REBOOT}" = "1" ]; then
			echo "Rebooting in 3 seconds..."
			ubus call system reboot "{ 'delay': 3 }"
		fi
		;;

	postupdate-failed)
		;;

	*)
		;;
esac

exit 0
