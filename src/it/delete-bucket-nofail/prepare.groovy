// ensure the bucket to be deleted does NOT exist by attempting to delete it
def curlOutput = "curl -i -X DELETE -u Administrator:couchbase http://localhost:8091/pools/default/buckets/some-fake-bucket".execute().text
assert curlOutput.contains("HTTP/1.1 404 Object Not Found")
