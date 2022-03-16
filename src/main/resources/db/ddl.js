db = connect(host);
db.auth(username, password);
db = db.getSiblingDB('IndigoDocumentStore');

const collections = [
    'EtlMetaData'
    'PostRiskMetaData',
    'DataQualityMetaData',
    'DataQualityConformedMetaData',
    'DataQualityEnrichedMetaData'
];

collections.forEach(coll => {
    print("Creating text index for collection " + coll);
    db.coll.createIndex({ "rulegroups.rules.value": "text", "rulegroups.rules.attributeName": "text" });
});
print("Text index creation completed.")
