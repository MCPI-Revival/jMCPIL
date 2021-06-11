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
mkdir -p debian/tmp/opt/jmcpil
rsync -r debian/common/ debian/tmp
# Copy JAR
cp build/libs/*-fat.jar debian/tmp/opt/jmcpil/jmcpil.jar
# Set Version
(git describe --tags --dirty || echo '0.1.0') > debian/tmp/opt/jmcpil/VERSION
# Add Changelog
pandoc --from gfm --to html --output debian/tmp/opt/jmcpil/CHANGELOG CHANGELOG.md

# Substitute
sed -i 's/${VERSION}/'"$(cat debian/tmp/opt/jmcpil/VERSION)"'/g' debian/tmp/DEBIAN/control

# Build DEB
dpkg-deb -b --root-owner-group debian/tmp out

# Clean Up
rm -rf debian/tmp
