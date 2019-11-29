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
echo "=============================="
echo "generating backend private key"
echo "=============================="
echo
# Backend Private Key erzeugen
# genrsa  Command um einen RSA Private Key zu erzeugen
openssl genrsa \
-out backend/privkey.pem \
4096
# output: Private Key des Backend: backend/privkey.pem
# Format: The PEM form is the default format: it consists of the DER format base64 encoded with additional header and footer lines.

echo
echo "=================================="
echo "generating backend signing request"
echo "=================================="
echo
# Backend Signing Request erzeugen
# Create a request from your Device, which your Root CA will sign
# NOTE: You MUST match CN to the domain name or ip address you want to use
#req    Command um ein Signing Request zu erzeugen
#-new   erzeugt den Signing Request, als Basis dient der Private Key der mit -key angegeben wird, fehlt -key, wird ein neuer Private Key erzeugt
#-key   der Private Key der signiert werden soll
openssl req -new \
-key backend/privkey.pem \
-out backend/csr.pem \
-subj "/C=DE/ST=Saxony/L=Dresden/O=OSP GmbH/CN=backend.example.com"
# output: Signing Request fÃ¼r das Backend Zertifkat: backend/csr.pem
# Format: The PEM form is the default format: it consists of the DER format base64 encoded with additional header and footer lines.

echo
echo "================================================="
echo "signing backend certificate with root certificate"
echo "================================================="
echo
# Signierung des Backend Signing Requests mit dem Root-Zertifikat
# Sign the request from Device with your Root CA
#x509   Command um ein Zertifikat anzuzeigen oder zu signieren
#-req   statt einem Zertifikat wird als Input (-in) ein Signing Request erwartet
#-CA    Zertifikat, welches als Certification Authority benutzt wird - hier: Root-Zertifikat (kÃ¶nnte aber auch ein Intermediate Zertifikat sein?)
#       The input file is signed by this CA using this option: that is its issuer name is set to the subject name of the CA and it is digitally signed using the CAs private key.
#-CAkey Private Key der CA der fÃ¼r das Signieren genutzt wird
#-CAcreateserial In Kombination mit der -CA Option wird hierduch eine zufÃ¤llige Seriennummer fÃ¼r das Zertifikat generiert (empfohlene Praxis)
openssl x509 \
-req -in backend/csr.pem \
-CA root/root-ca.crt.pem \
-CAkey root/root-ca.key.pem \
-CAcreateserial \
-out backend/cert.pem \
-days 500
# output: Durch das Root-Zertifikat signiertes Backend-Zertifikat: backend/cert.pem
# Format: The PEM form is the default format: it consists of the DER format base64 encoded with additional header and footer lines.
# Hinweis: Das entstandene Zertifikat kann sehr wahrscheinlich nicht als Intermediate Zertifikat eingesetzt werden das die entsprechende Extension fehlt: Basic Constraints: CA:TRUE

echo
echo "============================="
echo "generating backend public key"
echo "============================="
echo
#rsa      Command für Bearbeitng von RSA Keys
#-pubout  Public Key als Output (Default ist ein Private Key)
openssl rsa \
-in backend/privkey.pem \
-pubout -out backend/pubkey.pem

# check and print private key
# openssl rsa -in root/root-ca.crt.pem -check -text

# print certificate
#openssl x509 -in root/root-ca.crt.pem -text
