# Pidesco Code Generator
The purpose of this component is to incorporate in the pidesco ide the possibility to generate code by pressing the button corresponding to the generation function that the user wants. 
It provides the following services:
* CodeStringGeneratorService (which is also the extension point) - This service is responsible for building the strings of the different functionalities of the project. It builds strings for:
* * VariableName
* * Ifs
* * Binding Variables
* * Constructors
* * Constructors with binding variables
* * Setter
* * Getter
* * Fields
* * Method
* CodeGeneratorService - This service is combination of the previous one and the insertion logic of the project. It generates and inserts a string in a pre-determined place (depending on the functionality used) or in a certain line or offset if it's specified. This service provides the same functionalities as the previous one with insertions customization such as line and offset.

It provides the following extension point:
* CodeGenerationRelacement: Allows the person who extends it to replace the way the strings are generated. To use this extension a name and an implementation of CodeStringGeneratorService.java must be provided.
* CodeGenerationAdd: Allows the person who extends it to add new functionalities. To use this extension an implementation of CodeGeneratorFunctionAddExtension.java must be provided.

This project includes an integration with the Search Component (https://github.com/TiagoMartinhoS/pa-iscde-tfmss1) currently in the featureIntegration branch. It uses its services to search for specified string and comment all field and method occurences. It extends its extension point by adding the possibility to search for all the occurences of a variable.
