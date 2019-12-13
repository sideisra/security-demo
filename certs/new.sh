./cleanup.sh

# see: https://superuser.com/questions/126121/how-to-create-my-own-certificate-chain
echo
echo "==========================="
echo "generating root private key"
echo "==========================="
echo
# RSA Private Key für Root-Zertifikat generieren
# genrsa  Command um einen RSA Private Key zu erzeugen
openssl genrsa \
-out root/root-ca.key.pem \
4096
# output: Private Key des Root-Zertifikats: root/root-ca.key.pem
# Format: The PEM form is the default format: it consists of the DER format base64 encoded with additional header and footer lines.

echo
echo "======================================="
echo "generating self-signed root certificate"
echo "======================================="
echo
# Root Private Key signieren
#req    Command um ein Signing Request zu erzeugen
#-x509  statt eines Signing Request wird ein Self-Signed Zertifikat ausgegeben
#-new   erzeugt das Self-Signed Zertifkat, als Basis dient der Private Key der mit -key angegeben wird, fehlt -key, wird ein neuer Private Key erzeugt
#-nodes TODO weglassen???
#-key   certs/ca/my-root-ca.key.pem \  # der Private Key der signiert werden soll
#-days  wie lange ist das Zertifkat gÃ¼ltig?
openssl req \
-x509 \
-new \
-nodes \
-key root/root-ca.key.pem \
-days 1024 \
-out root/root-ca.crt.pem \
-subj "/C=DE/ST=Saxony/L=Dresden/O=OSP GmbH/CN=example.com"
# output: selbst signiertes Root-Zertifkat: root/root-ca.crt.pem
# Format: The PEM form is the default format: it consists of the DER format base64 encoded with additional header and footer lines.

echo
echo "========================================"
echo "transform root certificate to der format"
echo "========================================"
echo
openssl x509 -outform der -in root/root-ca.crt.pem -out root/root-ca.crt.der

echo
echo "============================================"
echo "import root certificate into a java keystore"
echo "============================================"
echo
keytool -import -alias my-root-ca -keystore root/truststore.p12 -file root/root-ca.crt.der -storepass changeme -noprompt -storetype PKCS12
cp root/truststore.p12 ../client/truststore.p12

# print keystore certs
#keytool -list -keystore backend/keystore.jks -storepass changeme

./renew_backend_cert.sh
