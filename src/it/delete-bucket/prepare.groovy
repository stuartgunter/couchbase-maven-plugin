// create the bucket to be deleted
def curlOutput = "curl -i -u Administrator:couchbase -d name=some-real-bucket -d ramQuotaMB=100 -d authType=none -d replicaNumber=1 -d proxyPort=11217 http://localhost:8091/pools/default/buckets".execute().text
assert curlOutput.contains("HTTP/1.1 202 Accepted")
