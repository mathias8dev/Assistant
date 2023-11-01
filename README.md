# Assistant

This project allows you to build chat based applications on top of GPT models using prompt tuning
  

## Installation

1. Clone the repository:

  

```shell

git clone https://github.com/mathias8dev/Assistant

cd into your directory/ open with AndroidStudio

```
2. Create OpenAI API Key and add it to your .env file:

[openai](https://platform.openai.com/)

3. The env

Open the local.properties file and add at the end of the file your OpenAI api key

```shell

OPENAI_API_KEY="YOUR_API_KEY"

```

4. Prompt-tuning

  Update the personality of your Bot.
  🤔 I think, for easy modifications, this should be updated from anywhere. I let you do the required modifications to do it.

```kotlin
object Assistant {

    private val personality = """
        You are a personal assistant. All of your responses are easy to understand and are
        human-like responses. Be friendly, kind and funny. 
        Your name is Assistant and your role is to assist people.
    """.trimIndent()

    val defaultPrompt: ChatPrompt
        get() {
            val currentLanguage = Locale.getDefault().displayLanguage
            return ChatPrompt(
                role = ChatRole.SYSTEM,
                content = personality +
                "By default, if the user does not write in another language, answer him in $currentLanguage"
            )
        }
}

```

5. Run the application:

Run the application and enjoy

  

## Next Steps

1. Update the theme
2. Update the app
3. Implement more functionalities
