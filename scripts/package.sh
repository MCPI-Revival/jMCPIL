#!/bin/sh

set -e

# Build
./gradlew fatJar

# Prepare
rm -rf debian/tmp
mkdir debian/tmp
rm -rf out
mkdir -p out

# Copy Debian Files
mkdir -p debian/tmp/opt/mcpil
rsync -r debian/common/ debian/tmp
# Copy JAR
cp build/libs/*-fat.jar debian/tmp/opt/mcpil/mcpil.jar
# Set Version
(git describe --tags --dirty || echo '0.1.0') > debian/tmp/opt/mcpil/VERSION
# Add Changelog
pandoc --from gfm --to html --output debian/tmp/opt/mcpil/CHANGELOG CHANGELOG.md

# Substitute
sed -i 's/${VERSION}/'"$(cat debian/tmp/opt/mcpil/VERSION)"'/g' debian/tmp/DEBIAN/control

# Build DEB
dpkg-deb -b --root-owner-group debian/tmp out

# Clean Up
rm -rf debian/tmp
