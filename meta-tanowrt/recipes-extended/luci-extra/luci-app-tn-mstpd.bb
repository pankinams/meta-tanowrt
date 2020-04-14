#
# LuCI support for MSTP daemon
#
# Copyright (c) 2018-2020, Tano Systems. All Rights Reserved.
# Anton Kikin <a.kikin@tano-systems.com>
#
PV = "1.3.2+git${SRCPV}"
PR = "tano1"

SUMMARY = "LuCI support for MSTP daemon"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=aed2cf5a7c273a7c2dcdbd491a3a8416"

GIT_BRANCH   = "master"
GIT_SRCREV   = "b8209a5c634a21c9d544f5d21dd37bf78d7d015c"
GIT_PROTOCOL = "https"
SRC_URI = "git://github.com/tano-systems/luci-app-tn-mstpd.git;branch=${GIT_BRANCH};protocol=${GIT_PROTOCOL}"

SRCREV = "${GIT_SRCREV}"

RDEPENDS_${PN} += "mstpd luci-compat"

S = "${WORKDIR}/git"

inherit openwrt-luci-app
inherit openwrt-luci-i18n

LUCI_APP_TN_MSTPD_HIDE_FOOTER ?= "1"

do_install_append() {
	install -d ${D}${sysconfdir}/uci-defaults

	UCIDEFFILE=${D}${sysconfdir}/uci-defaults/80_luci_app_tn_mstpd_footer

	echo "#!/bin/sh" > ${UCIDEFFILE}
	echo "uci -q batch <<-EOF >/dev/null" >> ${UCIDEFFILE}
	echo "    set luci.app_tn_mstpd=internal" >> ${UCIDEFFILE}
	echo "    set luci.app_tn_mstpd.hide_footer=${LUCI_APP_TN_MSTPD_HIDE_FOOTER}" >> ${UCIDEFFILE}
	echo "    commit luci" >> ${UCIDEFFILE}
	echo "EOF" >> ${UCIDEFFILE}
	echo "exit 0" >> ${UCIDEFFILE}
}
