package com.smarts.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * Created by yangxuejun
 * on 2017/5/23.15:30
 */
public class FirstLucene {
    public static void main(String[] args) throws IOException, ParseException {
        Version current = Version.LUCENE_44;
        Analyzer analyzer = new StandardAnalyzer(current);
        Directory directory = FSDirectory.open(new File("E:\\lucene"));
        IndexWriterConfig config = new IndexWriterConfig(current, analyzer);
        IndexWriter writer = new IndexWriter(directory, config);
        Document doc = new Document();
        doc.add(new Field("name", "张颖", TextField.TYPE_STORED));
//        doc.add(new Field("age", "30", TextField.TYPE_STORED));
//        writer.addDocument(doc);
        writer.deleteDocuments(new Term("\"颖\""));
//        writer.forceMerge(1);
        writer.commit();
        writer.close();


        DirectoryReader dr = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(dr);
        QueryParser parser = new QueryParser(Version.LUCENE_44,"name",analyzer);
        Query query = parser.parse("\"张颖\"");
        System.out.println(query.toString());
        ScoreDoc[] hits = searcher.search(query,null,1000).scoreDocs;
        for(int i=0;i<hits.length;i++){
            Document hitDoc = searcher.doc(hits[i].doc);
            System.out.println(hitDoc.get("name")+" : "+hitDoc.get("age"));
        }
        dr.close();
        directory.close();
    }
}
