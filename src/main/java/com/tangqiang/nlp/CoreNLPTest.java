package com.tangqiang.nlp;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

/**
 * 自然语言测试
 * 
 * 斯坦福大学的CoreNLP提供一系列的自然语言处理工具，输入原始英语文本，可以给出单词的基本形式
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-7-25 下午3:33:15
 * 
 * @version 1.0 2014-7-25 tqiang create
 * 
 */
public class CoreNLPTest {
	private Logger logger = Logger.getLogger(CoreNLPTest.class);

	public static void main(String[] args) {
		CoreNLPTest cnt = new CoreNLPTest();
		cnt.runTest();
	}

	private void runTest() {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		String text = " Add your text here! AIOAHsaksdi asd aspiodhw lakjsdb  askdnwq askdn zxcniuqwpd askcnasudhqkjnasdfjique ajdfiouaq asdfaiodhf";
		Annotation document = new Annotation(text);

		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String word = token.get(TextAnnotation.class);
				String pos = token.get(PartOfSpeechAnnotation.class);
				String ne = token.get(NamedEntityTagAnnotation.class);
				logger.info(word + "  " + pos + "  " + ne);
			}

			Tree tree = sentence.get(TreeAnnotation.class);

			SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);

		}

		Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
		logger.info("graph :" + graph);
	}

}
