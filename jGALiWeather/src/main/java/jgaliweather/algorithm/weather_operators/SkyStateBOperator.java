package jgaliweather.algorithm.weather_operators;

import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.data.data_structures.Variable;

/*
    Implements an operator which generates a cloud coverage
    linguistic description, including information about
    the sky state for the whole short-term, using quantifiers.
 */
public class SkyStateBOperator {
    
    private Partition sky_partition;
    private Partition quantifiers_partition;
    private Variable data;

    /*
        Initializes a SkyStateBOperator object

        :param sky_partition: An object partition set defining
        cloud coverage labels
        :param quantifiers_partition: A quantifier partition set
        :param data_series: The sky state forecast source data

        :return: A new SkyStateBOperator object
    */
    public SkyStateBOperator(Partition sky_partition, Partition quantifiers_partition, Variable data) {
        this.sky_partition = sky_partition;
        this.quantifiers_partition = quantifiers_partition;
        this.data = data;
    }
    
    
    /*
        Obtains a linguistic description of the cloud coverage state,
        as a matrix of index labels associated to quantifiers, including
        the percentage of cloud coverage labels

        :return: A linguistic description as a numpy matrix, containing
        indices to the most predominant cloud coverage labels and their
        associated numeric percentages
    */
    public String applyOperator() {
        
    }
}
