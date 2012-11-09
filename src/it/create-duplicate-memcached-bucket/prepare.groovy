// create the bucket to be duplicated
def curlOutput = "curl -i -u Administrator:couchbase -d name=test-duplicate-bucket -d ramQuotaMB=100 -d authType=none -d replicaNumber=1 -d proxyPort=11220 http://localhost:8091/pools/default/buckets".execute().text
assert curlOutput.contains("HTTP/1.1 202 Accepted")
