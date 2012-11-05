// make sure the bucket does not exist by attempting to delete it (serves as environment prep as well)
"curl -X DELETE -u Administrator:couchbase http://localhost:8091/pools/default/buckets/test-couchbase-bucket".execute()
assert true