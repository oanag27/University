using System;
using System.Collections.Generic;
using System.IO;

namespace ConfigurationLoader
{
    public class Configuration
    {
        private Dictionary<string, object> settings;

        public Configuration()
        {
            settings = new Dictionary<string, object>();
        }

        public void LoadFromJson(string filePath)
        {
            try
            {
                string json = File.ReadAllText(filePath);
                settings = ParseJson(json);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error loading configuration: {ex.Message}");
            }
        }

        private Dictionary<string, object> ParseJson(string json)
        {
            Dictionary<string, object> result = new Dictionary<string, object>();

            // Remove whitespace and curly braces
            json = json.Replace("{", "").Replace("}", "").Trim();

            // Split JSON string into key-value pairs
            string[] keyValuePairs = json.Split(',');

            foreach (string pair in keyValuePairs)
            {
                // Split each key-value pair into key and value
                string[] parts = pair.Split(':');


                if (parts.Length == 2) // Ensure correct format
                {
                    // Trim quotes and whitespace from key
                    string key = parts[0].Trim().Trim('"');

                    // Trim whitespace from value
                    string value = parts[1].Trim();

                    // Attempt to parse the value as an integer
                    if (int.TryParse(value, out int intValue))
                    {
                        result.Add(key, intValue);
                    }
                    // Attempt to parse the value as a boolean
                    else if (bool.TryParse(value, out bool boolValue))
                    {
                        result.Add(key, boolValue);
                    }
                    // Otherwise, treat the value as a string
                    else
                    {
                        result.Add(key, value.Trim('"'));
                    }
                }
            }

            return result;
        }

        public T GetSetting<T>(string key)
        {
            if (settings.ContainsKey(key))
            {
                object value = settings[key];
                if (value is T)
                {
                    return (T)value;
                }
                else
                {
                    Console.WriteLine($"Error: Setting '{key}' is not of type {typeof(T).Name}");
                }
            }
            else
            {
                Console.WriteLine($"Error: Setting '{key}' not found");
            }

            return default(T);
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            Configuration config = new Configuration();
            config.LoadFromJson("C:\\iss\\config.json");


            string apiKey = config.GetSetting<string>("apiKey");
            int timeout = config.GetSetting<int>("timeout");
            bool loggingEnabled = config.GetSetting<bool>("loggingEnabled");
            string tryC = config.GetSetting<string>("tryC");
            Console.WriteLine($"apiKey: {apiKey}");
            Console.WriteLine($"timeout: {timeout}");
            Console.WriteLine($"loggingEnabled: {loggingEnabled}");
            Console.WriteLine($"tryC: {tryC}");
        }
    }
}
