import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.util.ArrayList;

public class FakeNewsClassifier {

    private Classifier classifier;
    private StringToWordVector filter;
    private Instances trainingData;

    public void trainModel() throws Exception {

        // Load dataset
        DataSource source = new DataSource("dataset/indian_news.arff");
        trainingData = source.getDataSet();
        trainingData.setClassIndex(1);

        // Convert text to vectors
        filter = new StringToWordVector();
        filter.setInputFormat(trainingData);
        trainingData = Filter.useFilter(trainingData, filter);

        // Train Naive Bayes
        classifier = new NaiveBayes();
        classifier.buildClassifier(trainingData);

        System.out.println("Model trained successfully");
    }

    public String predict(String newsText) throws Exception {

        // Create test instance
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("text", (ArrayList<String>) null));

        ArrayList<String> classValues = new ArrayList<>();
        classValues.add("FAKE");
        classValues.add("REAL");
        attributes.add(new Attribute("class", classValues));

        Instances testData = new Instances("TestNews", attributes, 1);
        testData.setClassIndex(1);

        DenseInstance instance = new DenseInstance(2);
        instance.setValue(attributes.get(0), newsText);
        testData.add(instance);

        // Apply same filter
        testData = Filter.useFilter(testData, filter);

        double result = classifier.classifyInstance(testData.instance(0));
        return testData.classAttribute().value((int) result);
    }
                                     }
