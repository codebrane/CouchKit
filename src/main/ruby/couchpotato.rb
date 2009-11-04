require 'net/http'

class CouchDB
  attr_reader :couchdb_server
  
  def initialize(url)
    @couchdb_server = url
  end
  
  def create(db_name)
    potato("PUT", db_name)
  end
  
  def delete(db_name)
    potato("DELETE", db_name)
  end
  
  def info(db_name)
    potato("GET", db_name)
  end
  
  def add_doc(db_name, doc_id, doc_json)
    potato("PUT", "#{db_name}/#{doc_id}", doc_json)
  end
  
  def get_doc(db_name, doc_id)
    potato("GET", "#{db_name}/#{doc_id}")
  end
        
  def potato(mode, db_name, data="")
    uri = URI.parse("#{@couchdb_server}/#{db_name}")
    Net::HTTP.start(uri.host, uri.port) do |http|
      headers = {'Content-Type' => 'text/plain; charset=utf-8'}
      response = http.send_request("#{mode}", uri.request_uri, data, headers)
      puts "Response #{response.code} #{response.message}: #{response.body}"
    end
  end
end



