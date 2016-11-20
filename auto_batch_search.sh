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


replace "/Users/geleichen/Downloads/galago-3.10-bin/cacm/" "" -- CS473HW/outputTF.txt CS473HW/outputTFIDF.txt CS473HW/outputLogTFIDF.txt CS473HW/outputCosine.txt CS473HW/outputBM25.txt CS473HW/outputJM.txt
replace ".html" "" -- CS473HW/outputTF.txt CS473HW/outputTFIDF.txt CS473HW/outputLogTFIDF.txt CS473HW/outputCosine.txt CS473HW/outputBM25.txt CS473HW/outputJM.txt

core/target/appassembler/bin/galago eval --judgments=CS473HW/cacm.rel --runs+CS473HW/outputTF.txt --metrics+map --metrics+ndcg --metrics+P3 --details=true > CS473HW/resultTF.txt

core/target/appassembler/bin/galago eval --judgments=CS473HW/cacm.rel --runs+CS473HW/outputTFIDF.txt --metrics+map --metrics+ndcg --metrics+P3 --details=true > CS473HW/resultTFIDF.txt

core/target/appassembler/bin/galago eval --judgments=CS473HW/cacm.rel --runs+CS473HW/outputLogTFIDF.txt --metrics+map --metrics+ndcg --metrics+P3 --details=true > CS473HW/resultLogTFIDF.txt

core/target/appassembler/bin/galago eval --judgments=CS473HW/cacm.rel --runs+CS473HW/outputCosine.txt --metrics+map --metrics+ndcg --metrics+P3 --details=true > CS473HW/resultCosine.txt

core/target/appassembler/bin/galago eval --judgments=CS473HW/cacm.rel --runs+CS473HW/outputBM25.txt --metrics+map --metrics+ndcg --metrics+P3 --details=true > CS473HW/resultBM25.txt

core/target/appassembler/bin/galago eval --judgments=CS473HW/cacm.rel --runs+CS473HW/outputJM.txt --metrics+map --metrics+ndcg --metrics+P3 --details=true > CS473HW/resultJM.txt
