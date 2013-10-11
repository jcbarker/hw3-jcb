package edu.cmu.deiis.cpe;

import java.io.IOException;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.util.Progress;
import org.apache.uima.tools.components.FileSystemCollectionReader;

public class FSCollectionReader extends CollectionReader_ImplBase {
  FileSystemCollectionReader cr = new FileSystemCollectionReader();
  
  @Override
  public void getNext(CAS arg0) throws IOException, CollectionException {
    cr.getNext(arg0);
  }

  @Override
  public void close() throws IOException {
    cr.close();
  }

  @Override
  public Progress[] getProgress() {
    return cr.getProgress();
  }

  @Override
  public boolean hasNext() throws IOException, CollectionException {
    return cr.hasNext();
  }

}
