# Copyright (C) 2019, Fuzhou Rockchip Electronics Co., Ltd
# Released under the MIT license (see COPYING.MIT for the terms)

PR_append = ".rk0"

PACKAGECONFIG_GL = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2', '', d)}"
PACKAGECONFIG_FONTS = "fontconfig"

PACKAGECONFIG_append += "gbm kms eglfs linuxfb"
