require '../../main/ruby/couchpotato'

couchdb_server = "http://localhost:5984"
test_db_name = "testdb"
test_doc_id = 111
test_doc = <<-EOS
{
  "Subject":"I like Cakes!",
  "Author":"codeBrane",
  "PostedDate":"2009-11-03T17:30:12-04:00",
  "Tags":["cakes", "eating", "munching"],
  "Body":"I do like eating cakes you know!"
}
EOS

cdb = CouchDB.new(couchdb_server)

cdb.create(test_db_name)
puts "-------------------"

cdb.info(test_db_name)
puts "-------------------"

cdb.add_doc(test_db_name, test_doc_id, test_doc)
puts "-------------------"

cdb.get_doc(test_db_name, test_doc_id)
puts "-------------------"

cdb.delete(test_db_name)