# Training Tesseract 4.x for Bengali

#### Step 1: Extract recognition model

A recognition model can be extracted from an existing *traineddata* file. The output of this would be a *lstm* file.

    combine_tessdata -e /kaaj/installs/tesseract/tessdata_best-4.0.0/ben.traineddata ./ben.lstm

#### Step 2: Creating lstmf file from a tiff/box pair

We would need to obtain a *lstmf* binary file from a *tiff* image and its corresponding *box* file. The below command will create a *lstmf* binary file, given a *tiff* and *box* file pair:

    tesseract -l ben ben.rajshekhar_mahabharat.exp0.jpg ben.rajshekhar_mahabharat.exp0 --psm 6 lstm.train

You can create multiple *lstmf* files from several *tiff/box* pairs.  

#### Step 3: Creating a list of lstmf files

Create a *ben.training_files.txt* containing all the *lstmf* files that you have created in the previous step. The contents of this file will be the full path of each of the *lstmf* file:

    echo "ben.rajshekhar_mahabharat.exp0.lstmf" >> ben.training_files.txt

#### Step 4: Run training command    

Then run the below command to train:

    lstmtraining --model_output ./my_output \
    --continue_from ./ben.lstm \
    --traineddata /kaaj/installs/tesseract/tessdata_best-4.0.0/ben.traineddata \
    --train_listfile ./ben.training_files.txt \
    --max_iterations 400    

The output from the above will be a *my_output_checkpoint* file.

#### Step 5: Combining the outputs

In the final step, we have to combine the *my_output_checkpoint* file with the existing *traineddata* file

    lstmtraining --stop_training \
    --continue_from ./my_output_checkpoint \
    --traineddata /kaaj/installs/tesseract/tessdata_best-4.0.0/ben.traineddata \
    --model_output /kaaj/source/tessdata_best/ben.traineddata

You can now use the new *traineddata* file.        

