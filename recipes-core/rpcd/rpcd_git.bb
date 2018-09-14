# Copyright (C) 2015 Khem Raj <raj.khem@gmail.com>
# Copyright (C) 2018 Anton Kikin <a.kikin@tano-systems.com>
# Released under the MIT license (see COPYING.MIT for the terms)

PR = "tano3"
DESCRIPTION = "OpenWrt UBUS RPC server"
HOMEPAGE = "http://git.openwrt.org/?p=project/rpcd.git;a=summary"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://main.c;beginline=1;endline=18;md5=da5faf55ed0618f0dde1c88e76a0fc74"
SECTION = "base"
DEPENDS = "json-c libubox ubus uci iwinfo"

SRC_URI = "\
	git://git.openwrt.org/project/rpcd.git;name=rpcd; \
	"

# 09.08.2018
# uci: tighten uci reorder operation error handling
SRCREV_rpcd = "41333abee4c57e3de2bcfa08972954e2af20705a"

S = "${WORKDIR}/git"
OR = "${S}/openwrt/package/system/rpcd/files"

inherit cmake pkgconfig openwrt-services openwrt openwrt-base-files

OPENWRT_SERVICE_PACKAGES = "rpcd"
OPENWRT_SERVICE_SCRIPTS_rpcd += "rpcd"
OPENWRT_SERVICE_STATE_rpcd-rpcd ?= "enabled"

SRCREV_openwrt = "${OPENWRT_SRCREV}"

do_install_append() {
    install -d ${D}${includedir}/rpcd
    install -m 0644 ${S}/include/rpcd/* ${D}${includedir}/rpcd/
    install -Dm 0644 ${OR}/rpcd.config ${D}${sysconfdir}/config/rpcd
    install -Dm 0755 ${OR}/rpcd.init ${D}${sysconfdir}/init.d/rpcd

    mkdir -p ${D}/sbin
    ln -s /usr/sbin/rpcd ${D}/sbin/rpcd
}

FILES_${PN}  += "${libdir}/*"

RDEPENDS_${PN} += "iwinfo"

CONFFILES_${PN}_append = "\
	${sysconfdir}/config/rpcd \
"
