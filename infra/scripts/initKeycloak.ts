import KcAdminClient from "keycloak-admin";
import RealmRepresentation from "keycloak-admin/lib/defs/realmRepresentation";
import {readFileSync} from "fs";

async function initKeycloak() {
    console.log("initializing keycloak...");
    const kcAdminClient = new KcAdminClient({
        baseUrl: 'http://localhost:8181/auth',
    });

    await kcAdminClient.auth({
        username: 'admin',
        password: 'admin',
        grantType: 'password',
        clientId: 'admin-cli',
    });

    const clientRealmName = "security-demo";

    const realmExportFileData = readFileSync("realm-export.json", {encoding: "UTF-8"});
    const realmRep: RealmRepresentation = JSON.parse(realmExportFileData);

    const realm = await kcAdminClient.realms.findOne({
        realm: clientRealmName,
    });
    if (realm) {
        console.log(`Realm ${clientRealmName} already exists.`);
    } else {
        await kcAdminClient.realms.create(realmRep);
        console.log(`Realm ${clientRealmName} created.`);
    }
}

initKeycloak();

