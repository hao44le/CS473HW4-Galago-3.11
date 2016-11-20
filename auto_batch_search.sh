#!/bin/sh
cd ~/Downloads/lemur-galago
core/target/appassembler/bin/galago batch-search --index=CS473HW/cacm-index CS473HW/cacm.queryHW4.json --scorer=tf > CS473HW/outputTF.txt
echo "done with TF"

core/target/appassembler/bin/galago batch-search --index=CS473HW/cacm-index CS473HW/cacm.queryHW4.json --scorer=tfidf > CS473HW/outputTFIDF.txt
echo "done with TFIDF"

core/target/appassembler/bin/galago batch-search --index=CS473HW/cacm-index CS473HW/cacm.queryHW4.json --scorer=logtfidf > CS473HW/outputLogTFIDF.txt
echo "done with LogTFIDF"

core/target/appassembler/bin/galago batch-search --index=CS473HW/cacm-index CS473HW/cacm.queryHW4.json --scorer=cosine > CS473HW/outputCosine.txt
echo "done with Cosine"

core/target/appassembler/bin/galago batch-search --index=CS473HW/cacm-index CS473HW/cacm.queryHW4.json --scorer=bm25 > CS473HW/outputBM25.txt
echo "done with BM25"

core/target/appassembler/bin/galago batch-search --index=CS473HW/cacm-index CS473HW/cacm.queryHW4.json --scorer=jm > CS473HW/outputJM.txt
echo "done with JM"
