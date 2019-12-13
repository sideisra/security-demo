echo "======="
echo "cleanup"
echo "======="
echo
rm ../backend/src/main/resources/keystore/certAndKey.p12
rm ../client/truststore.p12
rm -r backend
rm -r root
mkdir backend
mkdir root
